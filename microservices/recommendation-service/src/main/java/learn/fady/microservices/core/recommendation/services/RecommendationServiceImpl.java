package learn.fady.microservices.core.recommendation.services;

import learn.fady.api.core.recommendation.Recommendation;
import learn.fady.api.core.recommendation.RecommendationService;
import learn.fady.microservices.core.recommendation.persistence.RecommendationEntity;
import learn.fady.microservices.core.recommendation.persistence.RecommendationRepository;
import learn.fady.util.exceptions.InvalidInputException;
import learn.fady.util.http.ServiceUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class RecommendationServiceImpl implements RecommendationService {
    private static final Logger LOG = LoggerFactory.getLogger(RecommendationServiceImpl.class);

    private final RecommendationRepository repository;
    private final RecommendationMapper mapper;
    private final ServiceUtil serviceUtil;

    @Autowired
    public RecommendationServiceImpl(RecommendationRepository repository, RecommendationMapper mapper, ServiceUtil serviceUtil) {
        this.repository = repository;
        this.mapper = mapper;
        this.serviceUtil = serviceUtil;
    }

    @Override
    public List<Recommendation> getRecommendations(int productId) {
        if (productId < 1) throw new InvalidInputException("Invalid productId: " + productId);
        List<RecommendationEntity> entityList = repository.findByProductId(productId);
        List<Recommendation> list = mapper.entityListToApiList(entityList);
        list.forEach(e -> e.setServiceAddress(serviceUtil.getServiceAddress()));
        LOG.debug("getRecommendations: response size: {}", list.size());
        return list;
    }

    @Override
    public Recommendation createRecommendation(Recommendation body) {
        try {
            RecommendationEntity entity = mapper.apiToEntity(body);
            RecommendationEntity newEntity = repository.save(entity);
            LOG.debug("createRecommendation; created a recommendation entity: {}/{}", body.getProductId(), body.getId());
            return mapper.entityToApi(newEntity);
        } catch (DataIntegrityViolationException dive){

            throw new InvalidInputException("Duplicate key, Product Id: " + body.getProductId() + ", Recommendation Id: " + body.getId());
        }
    }

    @Override
    public void deleteRecommendations(int productId) {
        LOG.debug("deleteRecommendations: tries to delete recommendations for the product with the ProductId: {}", productId);
        repository.deleteAll(repository.findByProductId(productId));
    }
}
