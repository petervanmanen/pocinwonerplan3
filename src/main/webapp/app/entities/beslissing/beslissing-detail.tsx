import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './beslissing.reducer';

export const BeslissingDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const beslissingEntity = useAppSelector(state => state.beslissing.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="beslissingDetailsHeading">Beslissing</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{beslissingEntity.id}</dd>
          <dt>
            <span id="datum">Datum</span>
          </dt>
          <dd>
            {beslissingEntity.datum ? <TextFormat value={beslissingEntity.datum} type="date" format={APP_LOCAL_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="opmerkingen">Opmerkingen</span>
          </dt>
          <dd>{beslissingEntity.opmerkingen}</dd>
          <dt>
            <span id="reden">Reden</span>
          </dt>
          <dd>{beslissingEntity.reden}</dd>
          <dt>Betreft Leerling</dt>
          <dd>{beslissingEntity.betreftLeerling ? beslissingEntity.betreftLeerling.id : ''}</dd>
          <dt>Behandelaar Leerplichtambtenaar</dt>
          <dd>{beslissingEntity.behandelaarLeerplichtambtenaar ? beslissingEntity.behandelaarLeerplichtambtenaar.id : ''}</dd>
          <dt>Betreft School</dt>
          <dd>{beslissingEntity.betreftSchool ? beslissingEntity.betreftSchool.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/beslissing" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/beslissing/${beslissingEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default BeslissingDetail;
