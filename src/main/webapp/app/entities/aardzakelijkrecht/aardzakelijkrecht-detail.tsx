import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './aardzakelijkrecht.reducer';

export const AardzakelijkrechtDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const aardzakelijkrechtEntity = useAppSelector(state => state.aardzakelijkrecht.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="aardzakelijkrechtDetailsHeading">Aardzakelijkrecht</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{aardzakelijkrechtEntity.id}</dd>
          <dt>
            <span id="codeaardzakelijkrecht">Codeaardzakelijkrecht</span>
          </dt>
          <dd>{aardzakelijkrechtEntity.codeaardzakelijkrecht}</dd>
          <dt>
            <span id="datumbegingeldigheidaardzakelijkrecht">Datumbegingeldigheidaardzakelijkrecht</span>
          </dt>
          <dd>
            {aardzakelijkrechtEntity.datumbegingeldigheidaardzakelijkrecht ? (
              <TextFormat
                value={aardzakelijkrechtEntity.datumbegingeldigheidaardzakelijkrecht}
                type="date"
                format={APP_LOCAL_DATE_FORMAT}
              />
            ) : null}
          </dd>
          <dt>
            <span id="datumeindegeldigheidaardzakelijkrecht">Datumeindegeldigheidaardzakelijkrecht</span>
          </dt>
          <dd>
            {aardzakelijkrechtEntity.datumeindegeldigheidaardzakelijkrecht ? (
              <TextFormat
                value={aardzakelijkrechtEntity.datumeindegeldigheidaardzakelijkrecht}
                type="date"
                format={APP_LOCAL_DATE_FORMAT}
              />
            ) : null}
          </dd>
          <dt>
            <span id="naamaardzakelijkrecht">Naamaardzakelijkrecht</span>
          </dt>
          <dd>{aardzakelijkrechtEntity.naamaardzakelijkrecht}</dd>
        </dl>
        <Button tag={Link} to="/aardzakelijkrecht" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/aardzakelijkrecht/${aardzakelijkrechtEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default AardzakelijkrechtDetail;
