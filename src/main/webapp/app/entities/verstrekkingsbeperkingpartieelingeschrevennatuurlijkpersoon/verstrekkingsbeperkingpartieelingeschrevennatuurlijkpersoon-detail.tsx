import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './verstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoon.reducer';

export const VerstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoonDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const verstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoonEntity = useAppSelector(
    state => state.verstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoon.entity,
  );
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="verstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoonDetailsHeading">
          Verstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoon
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{verstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoonEntity.id}</dd>
          <dt>
            <span id="gemeenteverordening">Gemeenteverordening</span>
          </dt>
          <dd>{verstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoonEntity.gemeenteverordening}</dd>
          <dt>
            <span id="omschrijvingderde">Omschrijvingderde</span>
          </dt>
          <dd>{verstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoonEntity.omschrijvingderde}</dd>
          <dt>
            <span id="partij">Partij</span>
          </dt>
          <dd>{verstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoonEntity.partij}</dd>
        </dl>
        <Button
          tag={Link}
          to="/verstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoon"
          replace
          color="info"
          data-cy="entityDetailsBackButton"
        >
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button
          tag={Link}
          to={`/verstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoon/${verstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoonEntity.id}/edit`}
          replace
          color="primary"
        >
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default VerstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoonDetail;
