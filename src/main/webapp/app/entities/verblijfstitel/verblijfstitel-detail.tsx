import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './verblijfstitel.reducer';

export const VerblijfstitelDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const verblijfstitelEntity = useAppSelector(state => state.verblijfstitel.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="verblijfstitelDetailsHeading">Verblijfstitel</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{verblijfstitelEntity.id}</dd>
          <dt>
            <span id="aanduidingverblijfstitel">Aanduidingverblijfstitel</span>
          </dt>
          <dd>{verblijfstitelEntity.aanduidingverblijfstitel}</dd>
          <dt>
            <span id="datumbegin">Datumbegin</span>
          </dt>
          <dd>
            {verblijfstitelEntity.datumbegin ? (
              <TextFormat value={verblijfstitelEntity.datumbegin} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="datumeinde">Datumeinde</span>
          </dt>
          <dd>
            {verblijfstitelEntity.datumeinde ? (
              <TextFormat value={verblijfstitelEntity.datumeinde} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="datumopname">Datumopname</span>
          </dt>
          <dd>
            {verblijfstitelEntity.datumopname ? (
              <TextFormat value={verblijfstitelEntity.datumopname} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="datumbegingeldigheidverblijfstitel">Datumbegingeldigheidverblijfstitel</span>
          </dt>
          <dd>
            {verblijfstitelEntity.datumbegingeldigheidverblijfstitel ? (
              <TextFormat value={verblijfstitelEntity.datumbegingeldigheidverblijfstitel} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="verblijfstitelcode">Verblijfstitelcode</span>
          </dt>
          <dd>{verblijfstitelEntity.verblijfstitelcode}</dd>
        </dl>
        <Button tag={Link} to="/verblijfstitel" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/verblijfstitel/${verblijfstitelEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default VerblijfstitelDetail;
