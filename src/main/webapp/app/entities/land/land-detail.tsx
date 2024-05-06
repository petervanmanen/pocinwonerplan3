import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './land.reducer';

export const LandDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const landEntity = useAppSelector(state => state.land.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="landDetailsHeading">Land</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{landEntity.id}</dd>
          <dt>
            <span id="datumeindefictief">Datumeindefictief</span>
          </dt>
          <dd>{landEntity.datumeindefictief ? 'true' : 'false'}</dd>
          <dt>
            <span id="datumeindeland">Datumeindeland</span>
          </dt>
          <dd>{landEntity.datumeindeland}</dd>
          <dt>
            <span id="datumingangland">Datumingangland</span>
          </dt>
          <dd>{landEntity.datumingangland}</dd>
          <dt>
            <span id="landcode">Landcode</span>
          </dt>
          <dd>{landEntity.landcode}</dd>
          <dt>
            <span id="landcodeisodrieletterig">Landcodeisodrieletterig</span>
          </dt>
          <dd>{landEntity.landcodeisodrieletterig}</dd>
          <dt>
            <span id="landcodeisotweeletterig">Landcodeisotweeletterig</span>
          </dt>
          <dd>{landEntity.landcodeisotweeletterig}</dd>
          <dt>
            <span id="landnaam">Landnaam</span>
          </dt>
          <dd>{landEntity.landnaam}</dd>
        </dl>
        <Button tag={Link} to="/land" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/land/${landEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default LandDetail;
