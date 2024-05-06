import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './documenttype.reducer';

export const DocumenttypeDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const documenttypeEntity = useAppSelector(state => state.documenttype.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="documenttypeDetailsHeading">Documenttype</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{documenttypeEntity.id}</dd>
          <dt>
            <span id="datumbegingeldigheiddocumenttype">Datumbegingeldigheiddocumenttype</span>
          </dt>
          <dd>{documenttypeEntity.datumbegingeldigheiddocumenttype}</dd>
          <dt>
            <span id="datumeindegeldigheiddocumenttype">Datumeindegeldigheiddocumenttype</span>
          </dt>
          <dd>{documenttypeEntity.datumeindegeldigheiddocumenttype}</dd>
          <dt>
            <span id="documentcategorie">Documentcategorie</span>
          </dt>
          <dd>{documenttypeEntity.documentcategorie}</dd>
          <dt>
            <span id="documenttypeomschrijving">Documenttypeomschrijving</span>
          </dt>
          <dd>{documenttypeEntity.documenttypeomschrijving}</dd>
          <dt>
            <span id="documenttypeomschrijvinggeneriek">Documenttypeomschrijvinggeneriek</span>
          </dt>
          <dd>{documenttypeEntity.documenttypeomschrijvinggeneriek}</dd>
          <dt>
            <span id="documenttypetrefwoord">Documenttypetrefwoord</span>
          </dt>
          <dd>{documenttypeEntity.documenttypetrefwoord}</dd>
        </dl>
        <Button tag={Link} to="/documenttype" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/documenttype/${documenttypeEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default DocumenttypeDetail;
