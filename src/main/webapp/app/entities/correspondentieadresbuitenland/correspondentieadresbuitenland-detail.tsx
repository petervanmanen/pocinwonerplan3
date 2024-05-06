import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './correspondentieadresbuitenland.reducer';

export const CorrespondentieadresbuitenlandDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const correspondentieadresbuitenlandEntity = useAppSelector(state => state.correspondentieadresbuitenland.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="correspondentieadresbuitenlandDetailsHeading">Correspondentieadresbuitenland</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{correspondentieadresbuitenlandEntity.id}</dd>
          <dt>
            <span id="adresbuitenland1">Adresbuitenland 1</span>
          </dt>
          <dd>{correspondentieadresbuitenlandEntity.adresbuitenland1}</dd>
          <dt>
            <span id="adresbuitenland2">Adresbuitenland 2</span>
          </dt>
          <dd>{correspondentieadresbuitenlandEntity.adresbuitenland2}</dd>
          <dt>
            <span id="adresbuitenland3">Adresbuitenland 3</span>
          </dt>
          <dd>{correspondentieadresbuitenlandEntity.adresbuitenland3}</dd>
          <dt>
            <span id="adresbuitenland4">Adresbuitenland 4</span>
          </dt>
          <dd>{correspondentieadresbuitenlandEntity.adresbuitenland4}</dd>
          <dt>
            <span id="adresbuitenland5">Adresbuitenland 5</span>
          </dt>
          <dd>{correspondentieadresbuitenlandEntity.adresbuitenland5}</dd>
          <dt>
            <span id="adresbuitenland6">Adresbuitenland 6</span>
          </dt>
          <dd>{correspondentieadresbuitenlandEntity.adresbuitenland6}</dd>
          <dt>
            <span id="landcorrespondentieadres">Landcorrespondentieadres</span>
          </dt>
          <dd>{correspondentieadresbuitenlandEntity.landcorrespondentieadres}</dd>
        </dl>
        <Button tag={Link} to="/correspondentieadresbuitenland" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/correspondentieadresbuitenland/${correspondentieadresbuitenlandEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default CorrespondentieadresbuitenlandDetail;
