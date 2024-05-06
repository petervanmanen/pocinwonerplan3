import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './splitsingstekeningreferentie.reducer';

export const SplitsingstekeningreferentieDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const splitsingstekeningreferentieEntity = useAppSelector(state => state.splitsingstekeningreferentie.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="splitsingstekeningreferentieDetailsHeading">Splitsingstekeningreferentie</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{splitsingstekeningreferentieEntity.id}</dd>
          <dt>
            <span id="bronorganisatie">Bronorganisatie</span>
          </dt>
          <dd>{splitsingstekeningreferentieEntity.bronorganisatie}</dd>
          <dt>
            <span id="datumcreatie">Datumcreatie</span>
          </dt>
          <dd>
            {splitsingstekeningreferentieEntity.datumcreatie ? (
              <TextFormat value={splitsingstekeningreferentieEntity.datumcreatie} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="identificatietekening">Identificatietekening</span>
          </dt>
          <dd>{splitsingstekeningreferentieEntity.identificatietekening}</dd>
          <dt>
            <span id="titel">Titel</span>
          </dt>
          <dd>{splitsingstekeningreferentieEntity.titel}</dd>
        </dl>
        <Button tag={Link} to="/splitsingstekeningreferentie" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/splitsingstekeningreferentie/${splitsingstekeningreferentieEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default SplitsingstekeningreferentieDetail;
