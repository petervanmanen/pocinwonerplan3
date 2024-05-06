import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './enkelvoudigdocument.reducer';

export const EnkelvoudigdocumentDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const enkelvoudigdocumentEntity = useAppSelector(state => state.enkelvoudigdocument.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="enkelvoudigdocumentDetailsHeading">Enkelvoudigdocument</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{enkelvoudigdocumentEntity.id}</dd>
          <dt>
            <span id="bestandsnaam">Bestandsnaam</span>
          </dt>
          <dd>{enkelvoudigdocumentEntity.bestandsnaam}</dd>
          <dt>
            <span id="documentformaat">Documentformaat</span>
          </dt>
          <dd>{enkelvoudigdocumentEntity.documentformaat}</dd>
          <dt>
            <span id="documentinhoud">Documentinhoud</span>
          </dt>
          <dd>{enkelvoudigdocumentEntity.documentinhoud}</dd>
          <dt>
            <span id="documentlink">Documentlink</span>
          </dt>
          <dd>{enkelvoudigdocumentEntity.documentlink}</dd>
          <dt>
            <span id="documentstatus">Documentstatus</span>
          </dt>
          <dd>{enkelvoudigdocumentEntity.documentstatus}</dd>
          <dt>
            <span id="documenttaal">Documenttaal</span>
          </dt>
          <dd>{enkelvoudigdocumentEntity.documenttaal}</dd>
          <dt>
            <span id="documentversie">Documentversie</span>
          </dt>
          <dd>{enkelvoudigdocumentEntity.documentversie}</dd>
        </dl>
        <Button tag={Link} to="/enkelvoudigdocument" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/enkelvoudigdocument/${enkelvoudigdocumentEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default EnkelvoudigdocumentDetail;
