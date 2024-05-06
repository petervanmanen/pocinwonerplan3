import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './categorie.reducer';

export const CategorieDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const categorieEntity = useAppSelector(state => state.categorie.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="categorieDetailsHeading">Categorie</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{categorieEntity.id}</dd>
          <dt>
            <span id="naam">Naam</span>
          </dt>
          <dd>{categorieEntity.naam}</dd>
          <dt>Gekwalificeerd Leverancier</dt>
          <dd>
            {categorieEntity.gekwalificeerdLeveranciers
              ? categorieEntity.gekwalificeerdLeveranciers.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.id}</a>
                    {categorieEntity.gekwalificeerdLeveranciers && i === categorieEntity.gekwalificeerdLeveranciers.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
        </dl>
        <Button tag={Link} to="/categorie" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/categorie/${categorieEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default CategorieDetail;
