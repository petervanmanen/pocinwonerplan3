import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './vloginfo.reducer';

export const VloginfoDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const vloginfoEntity = useAppSelector(state => state.vloginfo.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="vloginfoDetailsHeading">Vloginfo</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{vloginfoEntity.id}</dd>
          <dt>
            <span id="detectieverkeer">Detectieverkeer</span>
          </dt>
          <dd>{vloginfoEntity.detectieverkeer}</dd>
          <dt>
            <span id="eindegroen">Eindegroen</span>
          </dt>
          <dd>{vloginfoEntity.eindegroen ? 'true' : 'false'}</dd>
          <dt>
            <span id="snelheid">Snelheid</span>
          </dt>
          <dd>{vloginfoEntity.snelheid}</dd>
          <dt>
            <span id="startgroen">Startgroen</span>
          </dt>
          <dd>{vloginfoEntity.startgroen ? 'true' : 'false'}</dd>
          <dt>
            <span id="tijdstip">Tijdstip</span>
          </dt>
          <dd>{vloginfoEntity.tijdstip}</dd>
          <dt>
            <span id="verkeerwilgroen">Verkeerwilgroen</span>
          </dt>
          <dd>{vloginfoEntity.verkeerwilgroen ? 'true' : 'false'}</dd>
          <dt>
            <span id="wachttijd">Wachttijd</span>
          </dt>
          <dd>{vloginfoEntity.wachttijd}</dd>
        </dl>
        <Button tag={Link} to="/vloginfo" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/vloginfo/${vloginfoEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default VloginfoDetail;
