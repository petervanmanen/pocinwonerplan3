import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './reisdocument.reducer';

export const ReisdocumentDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const reisdocumentEntity = useAppSelector(state => state.reisdocument.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="reisdocumentDetailsHeading">Reisdocument</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{reisdocumentEntity.id}</dd>
          <dt>
            <span id="aanduidinginhoudingvermissing">Aanduidinginhoudingvermissing</span>
          </dt>
          <dd>{reisdocumentEntity.aanduidinginhoudingvermissing}</dd>
          <dt>
            <span id="autoriteitvanafgifte">Autoriteitvanafgifte</span>
          </dt>
          <dd>{reisdocumentEntity.autoriteitvanafgifte}</dd>
          <dt>
            <span id="datumeindegeldigheiddocument">Datumeindegeldigheiddocument</span>
          </dt>
          <dd>{reisdocumentEntity.datumeindegeldigheiddocument}</dd>
          <dt>
            <span id="datumingangdocument">Datumingangdocument</span>
          </dt>
          <dd>{reisdocumentEntity.datumingangdocument}</dd>
          <dt>
            <span id="datuminhoudingofvermissing">Datuminhoudingofvermissing</span>
          </dt>
          <dd>{reisdocumentEntity.datuminhoudingofvermissing}</dd>
          <dt>
            <span id="datumuitgifte">Datumuitgifte</span>
          </dt>
          <dd>{reisdocumentEntity.datumuitgifte}</dd>
          <dt>
            <span id="reisdocumentnummer">Reisdocumentnummer</span>
          </dt>
          <dd>{reisdocumentEntity.reisdocumentnummer}</dd>
          <dt>
            <span id="soort">Soort</span>
          </dt>
          <dd>{reisdocumentEntity.soort}</dd>
        </dl>
        <Button tag={Link} to="/reisdocument" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/reisdocument/${reisdocumentEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default ReisdocumentDetail;
