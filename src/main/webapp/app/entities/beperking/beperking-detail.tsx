import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './beperking.reducer';

export const BeperkingDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const beperkingEntity = useAppSelector(state => state.beperking.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="beperkingDetailsHeading">Beperking</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{beperkingEntity.id}</dd>
          <dt>
            <span id="categorie">Categorie</span>
          </dt>
          <dd>{beperkingEntity.categorie}</dd>
          <dt>
            <span id="commentaar">Commentaar</span>
          </dt>
          <dd>{beperkingEntity.commentaar}</dd>
          <dt>
            <span id="duur">Duur</span>
          </dt>
          <dd>{beperkingEntity.duur}</dd>
          <dt>
            <span id="wet">Wet</span>
          </dt>
          <dd>{beperkingEntity.wet}</dd>
          <dt>Iseen Beperkingscategorie</dt>
          <dd>{beperkingEntity.iseenBeperkingscategorie ? beperkingEntity.iseenBeperkingscategorie.id : ''}</dd>
          <dt>Isgebaseerdop Beschikking</dt>
          <dd>{beperkingEntity.isgebaseerdopBeschikking ? beperkingEntity.isgebaseerdopBeschikking.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/beperking" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/beperking/${beperkingEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default BeperkingDetail;
