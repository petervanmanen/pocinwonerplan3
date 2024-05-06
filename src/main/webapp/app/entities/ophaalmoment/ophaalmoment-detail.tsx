import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './ophaalmoment.reducer';

export const OphaalmomentDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const ophaalmomentEntity = useAppSelector(state => state.ophaalmoment.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="ophaalmomentDetailsHeading">Ophaalmoment</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{ophaalmomentEntity.id}</dd>
          <dt>
            <span id="gewichtstoename">Gewichtstoename</span>
          </dt>
          <dd>{ophaalmomentEntity.gewichtstoename}</dd>
          <dt>
            <span id="tijdstip">Tijdstip</span>
          </dt>
          <dd>{ophaalmomentEntity.tijdstip}</dd>
          <dt>Gelost Container</dt>
          <dd>{ophaalmomentEntity.gelostContainer ? ophaalmomentEntity.gelostContainer.id : ''}</dd>
          <dt>Gestoptop Locatie</dt>
          <dd>{ophaalmomentEntity.gestoptopLocatie ? ophaalmomentEntity.gestoptopLocatie.id : ''}</dd>
          <dt>Heeft Rit</dt>
          <dd>{ophaalmomentEntity.heeftRit ? ophaalmomentEntity.heeftRit.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/ophaalmoment" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/ophaalmoment/${ophaalmomentEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default OphaalmomentDetail;
