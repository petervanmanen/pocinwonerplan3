import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './kademuur.reducer';

export const KademuurDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const kademuurEntity = useAppSelector(state => state.kademuur.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="kademuurDetailsHeading">Kademuur</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{kademuurEntity.id}</dd>
          <dt>
            <span id="belastingklassenieuw">Belastingklassenieuw</span>
          </dt>
          <dd>{kademuurEntity.belastingklassenieuw}</dd>
          <dt>
            <span id="belastingklasseoud">Belastingklasseoud</span>
          </dt>
          <dd>{kademuurEntity.belastingklasseoud}</dd>
          <dt>
            <span id="grijpstenen">Grijpstenen</span>
          </dt>
          <dd>{kademuurEntity.grijpstenen ? 'true' : 'false'}</dd>
          <dt>
            <span id="hoogtebovenkantkademuur">Hoogtebovenkantkademuur</span>
          </dt>
          <dd>{kademuurEntity.hoogtebovenkantkademuur}</dd>
          <dt>
            <span id="materiaalbovenkantkademuur">Materiaalbovenkantkademuur</span>
          </dt>
          <dd>{kademuurEntity.materiaalbovenkantkademuur}</dd>
          <dt>
            <span id="oppervlaktebovenkantkademuur">Oppervlaktebovenkantkademuur</span>
          </dt>
          <dd>{kademuurEntity.oppervlaktebovenkantkademuur}</dd>
          <dt>
            <span id="reddingslijn">Reddingslijn</span>
          </dt>
          <dd>{kademuurEntity.reddingslijn ? 'true' : 'false'}</dd>
          <dt>
            <span id="type">Type</span>
          </dt>
          <dd>{kademuurEntity.type}</dd>
          <dt>
            <span id="typebovenkantkademuur">Typebovenkantkademuur</span>
          </dt>
          <dd>{kademuurEntity.typebovenkantkademuur}</dd>
          <dt>
            <span id="typefundering">Typefundering</span>
          </dt>
          <dd>{kademuurEntity.typefundering}</dd>
          <dt>
            <span id="typeverankering">Typeverankering</span>
          </dt>
          <dd>{kademuurEntity.typeverankering}</dd>
        </dl>
        <Button tag={Link} to="/kademuur" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/kademuur/${kademuurEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default KademuurDetail;
