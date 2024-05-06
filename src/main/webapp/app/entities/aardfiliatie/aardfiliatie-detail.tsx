import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './aardfiliatie.reducer';

export const AardfiliatieDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const aardfiliatieEntity = useAppSelector(state => state.aardfiliatie.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="aardfiliatieDetailsHeading">Aardfiliatie</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{aardfiliatieEntity.id}</dd>
          <dt>
            <span id="codeaardfiliatie">Codeaardfiliatie</span>
          </dt>
          <dd>{aardfiliatieEntity.codeaardfiliatie}</dd>
          <dt>
            <span id="datumbegingeldigheidaardfiliatie">Datumbegingeldigheidaardfiliatie</span>
          </dt>
          <dd>
            {aardfiliatieEntity.datumbegingeldigheidaardfiliatie ? (
              <TextFormat value={aardfiliatieEntity.datumbegingeldigheidaardfiliatie} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="datumeindegeldigheidaardfiliatie">Datumeindegeldigheidaardfiliatie</span>
          </dt>
          <dd>
            {aardfiliatieEntity.datumeindegeldigheidaardfiliatie ? (
              <TextFormat value={aardfiliatieEntity.datumeindegeldigheidaardfiliatie} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="naamaardfiliatie">Naamaardfiliatie</span>
          </dt>
          <dd>{aardfiliatieEntity.naamaardfiliatie}</dd>
        </dl>
        <Button tag={Link} to="/aardfiliatie" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/aardfiliatie/${aardfiliatieEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default AardfiliatieDetail;
