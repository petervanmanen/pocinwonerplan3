import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './leidingelement.reducer';

export const LeidingelementDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const leidingelementEntity = useAppSelector(state => state.leidingelement.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="leidingelementDetailsHeading">Leidingelement</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{leidingelementEntity.id}</dd>
          <dt>
            <span id="afwijkendedieptelegging">Afwijkendedieptelegging</span>
          </dt>
          <dd>{leidingelementEntity.afwijkendedieptelegging}</dd>
          <dt>
            <span id="diepte">Diepte</span>
          </dt>
          <dd>{leidingelementEntity.diepte}</dd>
          <dt>
            <span id="geonauwkeurigheidxy">Geonauwkeurigheidxy</span>
          </dt>
          <dd>{leidingelementEntity.geonauwkeurigheidxy}</dd>
          <dt>
            <span id="jaaronderhouduitgevoerd">Jaaronderhouduitgevoerd</span>
          </dt>
          <dd>{leidingelementEntity.jaaronderhouduitgevoerd}</dd>
          <dt>
            <span id="leverancier">Leverancier</span>
          </dt>
          <dd>{leidingelementEntity.leverancier}</dd>
          <dt>
            <span id="themaimkl">Themaimkl</span>
          </dt>
          <dd>{leidingelementEntity.themaimkl}</dd>
        </dl>
        <Button tag={Link} to="/leidingelement" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/leidingelement/${leidingelementEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default LeidingelementDetail;
