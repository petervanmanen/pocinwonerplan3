import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './beperkingscategorie.reducer';

export const BeperkingscategorieDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const beperkingscategorieEntity = useAppSelector(state => state.beperkingscategorie.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="beperkingscategorieDetailsHeading">Beperkingscategorie</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{beperkingscategorieEntity.id}</dd>
          <dt>
            <span id="code">Code</span>
          </dt>
          <dd>{beperkingscategorieEntity.code}</dd>
          <dt>
            <span id="wet">Wet</span>
          </dt>
          <dd>{beperkingscategorieEntity.wet}</dd>
        </dl>
        <Button tag={Link} to="/beperkingscategorie" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/beperkingscategorie/${beperkingscategorieEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default BeperkingscategorieDetail;
