import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './versie.reducer';

export const VersieDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const versieEntity = useAppSelector(state => state.versie.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="versieDetailsHeading">Versie</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{versieEntity.id}</dd>
          <dt>
            <span id="aantal">Aantal</span>
          </dt>
          <dd>{versieEntity.aantal}</dd>
          <dt>
            <span id="datumeindesupport">Datumeindesupport</span>
          </dt>
          <dd>
            {versieEntity.datumeindesupport ? (
              <TextFormat value={versieEntity.datumeindesupport} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="kosten">Kosten</span>
          </dt>
          <dd>{versieEntity.kosten}</dd>
          <dt>
            <span id="licentie">Licentie</span>
          </dt>
          <dd>{versieEntity.licentie}</dd>
          <dt>
            <span id="status">Status</span>
          </dt>
          <dd>{versieEntity.status}</dd>
          <dt>
            <span id="versienummer">Versienummer</span>
          </dt>
          <dd>{versieEntity.versienummer}</dd>
          <dt>Heeftversies Applicatie</dt>
          <dd>{versieEntity.heeftversiesApplicatie ? versieEntity.heeftversiesApplicatie.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/versie" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/versie/${versieEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default VersieDetail;
