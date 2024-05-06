import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './akrkadastralegemeentecode.reducer';

export const AkrkadastralegemeentecodeDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const akrkadastralegemeentecodeEntity = useAppSelector(state => state.akrkadastralegemeentecode.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="akrkadastralegemeentecodeDetailsHeading">Akrkadastralegemeentecode</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{akrkadastralegemeentecodeEntity.id}</dd>
          <dt>
            <span id="akrcode">Akrcode</span>
          </dt>
          <dd>{akrkadastralegemeentecodeEntity.akrcode}</dd>
          <dt>
            <span id="codeakrkadadastralegemeentecode">Codeakrkadadastralegemeentecode</span>
          </dt>
          <dd>{akrkadastralegemeentecodeEntity.codeakrkadadastralegemeentecode}</dd>
          <dt>
            <span id="datumbegingeldigheidakrcode">Datumbegingeldigheidakrcode</span>
          </dt>
          <dd>
            {akrkadastralegemeentecodeEntity.datumbegingeldigheidakrcode ? (
              <TextFormat value={akrkadastralegemeentecodeEntity.datumbegingeldigheidakrcode} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="datumeindegeldigheidakrcode">Datumeindegeldigheidakrcode</span>
          </dt>
          <dd>
            {akrkadastralegemeentecodeEntity.datumeindegeldigheidakrcode ? (
              <TextFormat value={akrkadastralegemeentecodeEntity.datumeindegeldigheidakrcode} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
        </dl>
        <Button tag={Link} to="/akrkadastralegemeentecode" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/akrkadastralegemeentecode/${akrkadastralegemeentecodeEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default AkrkadastralegemeentecodeDetail;
