import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './aanvraagofmelding.reducer';

export const AanvraagofmeldingDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const aanvraagofmeldingEntity = useAppSelector(state => state.aanvraagofmelding.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="aanvraagofmeldingDetailsHeading">Aanvraagofmelding</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{aanvraagofmeldingEntity.id}</dd>
          <dt>
            <span id="datum">Datum</span>
          </dt>
          <dd>
            {aanvraagofmeldingEntity.datum ? (
              <TextFormat value={aanvraagofmeldingEntity.datum} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="opmerkingen">Opmerkingen</span>
          </dt>
          <dd>{aanvraagofmeldingEntity.opmerkingen}</dd>
          <dt>
            <span id="reden">Reden</span>
          </dt>
          <dd>{aanvraagofmeldingEntity.reden}</dd>
          <dt>
            <span id="soortverzuimofaanvraag">Soortverzuimofaanvraag</span>
          </dt>
          <dd>{aanvraagofmeldingEntity.soortverzuimofaanvraag}</dd>
        </dl>
        <Button tag={Link} to="/aanvraagofmelding" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/aanvraagofmelding/${aanvraagofmeldingEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default AanvraagofmeldingDetail;
