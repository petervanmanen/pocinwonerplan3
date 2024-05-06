import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './naamnatuurlijkpersoon.reducer';

export const NaamnatuurlijkpersoonDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const naamnatuurlijkpersoonEntity = useAppSelector(state => state.naamnatuurlijkpersoon.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="naamnatuurlijkpersoonDetailsHeading">Naamnatuurlijkpersoon</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{naamnatuurlijkpersoonEntity.id}</dd>
          <dt>
            <span id="adellijketitelofpredikaat">Adellijketitelofpredikaat</span>
          </dt>
          <dd>{naamnatuurlijkpersoonEntity.adellijketitelofpredikaat}</dd>
          <dt>
            <span id="geslachtsnaam">Geslachtsnaam</span>
          </dt>
          <dd>{naamnatuurlijkpersoonEntity.geslachtsnaam}</dd>
          <dt>
            <span id="voornamen">Voornamen</span>
          </dt>
          <dd>{naamnatuurlijkpersoonEntity.voornamen}</dd>
          <dt>
            <span id="voorvoegselgeslachtsnaam">Voorvoegselgeslachtsnaam</span>
          </dt>
          <dd>{naamnatuurlijkpersoonEntity.voorvoegselgeslachtsnaam}</dd>
        </dl>
        <Button tag={Link} to="/naamnatuurlijkpersoon" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/naamnatuurlijkpersoon/${naamnatuurlijkpersoonEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default NaamnatuurlijkpersoonDetail;
