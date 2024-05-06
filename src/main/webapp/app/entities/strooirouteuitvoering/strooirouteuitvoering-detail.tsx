import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './strooirouteuitvoering.reducer';

export const StrooirouteuitvoeringDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const strooirouteuitvoeringEntity = useAppSelector(state => state.strooirouteuitvoering.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="strooirouteuitvoeringDetailsHeading">Strooirouteuitvoering</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{strooirouteuitvoeringEntity.id}</dd>
          <dt>
            <span id="geplandeinde">Geplandeinde</span>
          </dt>
          <dd>{strooirouteuitvoeringEntity.geplandeinde}</dd>
          <dt>
            <span id="geplandstart">Geplandstart</span>
          </dt>
          <dd>{strooirouteuitvoeringEntity.geplandstart}</dd>
          <dt>
            <span id="eroute">Eroute</span>
          </dt>
          <dd>{strooirouteuitvoeringEntity.eroute}</dd>
          <dt>
            <span id="werkelijkeinde">Werkelijkeinde</span>
          </dt>
          <dd>{strooirouteuitvoeringEntity.werkelijkeinde}</dd>
          <dt>
            <span id="werkelijkestart">Werkelijkestart</span>
          </dt>
          <dd>{strooirouteuitvoeringEntity.werkelijkestart}</dd>
        </dl>
        <Button tag={Link} to="/strooirouteuitvoering" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/strooirouteuitvoering/${strooirouteuitvoeringEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default StrooirouteuitvoeringDetail;
