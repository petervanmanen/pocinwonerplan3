import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './opschortingzaak.reducer';

export const OpschortingzaakDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const opschortingzaakEntity = useAppSelector(state => state.opschortingzaak.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="opschortingzaakDetailsHeading">Opschortingzaak</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{opschortingzaakEntity.id}</dd>
          <dt>
            <span id="indicatieopschorting">Indicatieopschorting</span>
          </dt>
          <dd>{opschortingzaakEntity.indicatieopschorting}</dd>
          <dt>
            <span id="redenopschorting">Redenopschorting</span>
          </dt>
          <dd>{opschortingzaakEntity.redenopschorting}</dd>
        </dl>
        <Button tag={Link} to="/opschortingzaak" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/opschortingzaak/${opschortingzaakEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default OpschortingzaakDetail;
