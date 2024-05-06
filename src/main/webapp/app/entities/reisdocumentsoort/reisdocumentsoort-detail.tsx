import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './reisdocumentsoort.reducer';

export const ReisdocumentsoortDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const reisdocumentsoortEntity = useAppSelector(state => state.reisdocumentsoort.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="reisdocumentsoortDetailsHeading">Reisdocumentsoort</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{reisdocumentsoortEntity.id}</dd>
          <dt>
            <span id="datumbegingeldigheidreisdocumentsoort">Datumbegingeldigheidreisdocumentsoort</span>
          </dt>
          <dd>
            {reisdocumentsoortEntity.datumbegingeldigheidreisdocumentsoort ? (
              <TextFormat
                value={reisdocumentsoortEntity.datumbegingeldigheidreisdocumentsoort}
                type="date"
                format={APP_LOCAL_DATE_FORMAT}
              />
            ) : null}
          </dd>
          <dt>
            <span id="datumeindegeldigheidreisdocumentsoort">Datumeindegeldigheidreisdocumentsoort</span>
          </dt>
          <dd>
            {reisdocumentsoortEntity.datumeindegeldigheidreisdocumentsoort ? (
              <TextFormat
                value={reisdocumentsoortEntity.datumeindegeldigheidreisdocumentsoort}
                type="date"
                format={APP_LOCAL_DATE_FORMAT}
              />
            ) : null}
          </dd>
          <dt>
            <span id="reisdocumentcode">Reisdocumentcode</span>
          </dt>
          <dd>{reisdocumentsoortEntity.reisdocumentcode}</dd>
          <dt>
            <span id="reisdocumentomschrijving">Reisdocumentomschrijving</span>
          </dt>
          <dd>{reisdocumentsoortEntity.reisdocumentomschrijving}</dd>
        </dl>
        <Button tag={Link} to="/reisdocumentsoort" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/reisdocumentsoort/${reisdocumentsoortEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default ReisdocumentsoortDetail;
