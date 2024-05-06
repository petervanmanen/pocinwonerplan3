import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './verblijfbuitenland.reducer';

export const VerblijfbuitenlandDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const verblijfbuitenlandEntity = useAppSelector(state => state.verblijfbuitenland.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="verblijfbuitenlandDetailsHeading">Verblijfbuitenland</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{verblijfbuitenlandEntity.id}</dd>
          <dt>
            <span id="adresregelbuitenland1">Adresregelbuitenland 1</span>
          </dt>
          <dd>{verblijfbuitenlandEntity.adresregelbuitenland1}</dd>
          <dt>
            <span id="adresregelbuitenland2">Adresregelbuitenland 2</span>
          </dt>
          <dd>{verblijfbuitenlandEntity.adresregelbuitenland2}</dd>
          <dt>
            <span id="adresregelbuitenland3">Adresregelbuitenland 3</span>
          </dt>
          <dd>{verblijfbuitenlandEntity.adresregelbuitenland3}</dd>
          <dt>
            <span id="adresregelbuitenland4">Adresregelbuitenland 4</span>
          </dt>
          <dd>{verblijfbuitenlandEntity.adresregelbuitenland4}</dd>
          <dt>
            <span id="adresregelbuitenland5">Adresregelbuitenland 5</span>
          </dt>
          <dd>{verblijfbuitenlandEntity.adresregelbuitenland5}</dd>
          <dt>
            <span id="adresregelbuitenland6">Adresregelbuitenland 6</span>
          </dt>
          <dd>{verblijfbuitenlandEntity.adresregelbuitenland6}</dd>
          <dt>
            <span id="landofgebiedverblijfadres">Landofgebiedverblijfadres</span>
          </dt>
          <dd>{verblijfbuitenlandEntity.landofgebiedverblijfadres}</dd>
        </dl>
        <Button tag={Link} to="/verblijfbuitenland" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/verblijfbuitenland/${verblijfbuitenlandEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default VerblijfbuitenlandDetail;
