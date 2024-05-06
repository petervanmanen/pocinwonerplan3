import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './bouwtype.reducer';

export const BouwtypeDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const bouwtypeEntity = useAppSelector(state => state.bouwtype.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="bouwtypeDetailsHeading">Bouwtype</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{bouwtypeEntity.id}</dd>
          <dt>
            <span id="hoofdcategorie">Hoofdcategorie</span>
          </dt>
          <dd>{bouwtypeEntity.hoofdcategorie}</dd>
          <dt>
            <span id="subcategorie">Subcategorie</span>
          </dt>
          <dd>{bouwtypeEntity.subcategorie}</dd>
          <dt>
            <span id="toelichting">Toelichting</span>
          </dt>
          <dd>{bouwtypeEntity.toelichting}</dd>
        </dl>
        <Button tag={Link} to="/bouwtype" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/bouwtype/${bouwtypeEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default BouwtypeDetail;
