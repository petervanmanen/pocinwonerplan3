import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './samengesteldenaamnatuurlijkpersoon.reducer';

export const SamengesteldenaamnatuurlijkpersoonDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const samengesteldenaamnatuurlijkpersoonEntity = useAppSelector(state => state.samengesteldenaamnatuurlijkpersoon.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="samengesteldenaamnatuurlijkpersoonDetailsHeading">Samengesteldenaamnatuurlijkpersoon</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{samengesteldenaamnatuurlijkpersoonEntity.id}</dd>
          <dt>
            <span id="adellijketitel">Adellijketitel</span>
          </dt>
          <dd>{samengesteldenaamnatuurlijkpersoonEntity.adellijketitel}</dd>
          <dt>
            <span id="geslachtsnaamstam">Geslachtsnaamstam</span>
          </dt>
          <dd>{samengesteldenaamnatuurlijkpersoonEntity.geslachtsnaamstam}</dd>
          <dt>
            <span id="namenreeks">Namenreeks</span>
          </dt>
          <dd>{samengesteldenaamnatuurlijkpersoonEntity.namenreeks}</dd>
          <dt>
            <span id="predicaat">Predicaat</span>
          </dt>
          <dd>{samengesteldenaamnatuurlijkpersoonEntity.predicaat}</dd>
          <dt>
            <span id="scheidingsteken">Scheidingsteken</span>
          </dt>
          <dd>{samengesteldenaamnatuurlijkpersoonEntity.scheidingsteken}</dd>
          <dt>
            <span id="voornamen">Voornamen</span>
          </dt>
          <dd>{samengesteldenaamnatuurlijkpersoonEntity.voornamen}</dd>
          <dt>
            <span id="voorvoegsel">Voorvoegsel</span>
          </dt>
          <dd>{samengesteldenaamnatuurlijkpersoonEntity.voorvoegsel}</dd>
        </dl>
        <Button tag={Link} to="/samengesteldenaamnatuurlijkpersoon" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button
          tag={Link}
          to={`/samengesteldenaamnatuurlijkpersoon/${samengesteldenaamnatuurlijkpersoonEntity.id}/edit`}
          replace
          color="primary"
        >
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default SamengesteldenaamnatuurlijkpersoonDetail;
