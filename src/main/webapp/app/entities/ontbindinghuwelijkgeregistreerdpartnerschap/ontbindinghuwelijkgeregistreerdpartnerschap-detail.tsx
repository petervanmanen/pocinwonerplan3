import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './ontbindinghuwelijkgeregistreerdpartnerschap.reducer';

export const OntbindinghuwelijkgeregistreerdpartnerschapDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const ontbindinghuwelijkgeregistreerdpartnerschapEntity = useAppSelector(
    state => state.ontbindinghuwelijkgeregistreerdpartnerschap.entity,
  );
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="ontbindinghuwelijkgeregistreerdpartnerschapDetailsHeading">Ontbindinghuwelijkgeregistreerdpartnerschap</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{ontbindinghuwelijkgeregistreerdpartnerschapEntity.id}</dd>
          <dt>
            <span id="buitenlandseplaatseinde">Buitenlandseplaatseinde</span>
          </dt>
          <dd>{ontbindinghuwelijkgeregistreerdpartnerschapEntity.buitenlandseplaatseinde}</dd>
          <dt>
            <span id="buitenlandseregioeinde">Buitenlandseregioeinde</span>
          </dt>
          <dd>{ontbindinghuwelijkgeregistreerdpartnerschapEntity.buitenlandseregioeinde}</dd>
          <dt>
            <span id="datumeinde">Datumeinde</span>
          </dt>
          <dd>{ontbindinghuwelijkgeregistreerdpartnerschapEntity.datumeinde}</dd>
          <dt>
            <span id="gemeenteeinde">Gemeenteeinde</span>
          </dt>
          <dd>{ontbindinghuwelijkgeregistreerdpartnerschapEntity.gemeenteeinde}</dd>
          <dt>
            <span id="landofgebiedeinde">Landofgebiedeinde</span>
          </dt>
          <dd>{ontbindinghuwelijkgeregistreerdpartnerschapEntity.landofgebiedeinde}</dd>
          <dt>
            <span id="omschrijvinglocatieeinde">Omschrijvinglocatieeinde</span>
          </dt>
          <dd>{ontbindinghuwelijkgeregistreerdpartnerschapEntity.omschrijvinglocatieeinde}</dd>
          <dt>
            <span id="redeneinde">Redeneinde</span>
          </dt>
          <dd>{ontbindinghuwelijkgeregistreerdpartnerschapEntity.redeneinde}</dd>
        </dl>
        <Button tag={Link} to="/ontbindinghuwelijkgeregistreerdpartnerschap" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button
          tag={Link}
          to={`/ontbindinghuwelijkgeregistreerdpartnerschap/${ontbindinghuwelijkgeregistreerdpartnerschapEntity.id}/edit`}
          replace
          color="primary"
        >
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default OntbindinghuwelijkgeregistreerdpartnerschapDetail;
