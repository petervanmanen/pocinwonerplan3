import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './subrekening.reducer';

export const SubrekeningDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const subrekeningEntity = useAppSelector(state => state.subrekening.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="subrekeningDetailsHeading">Subrekening</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{subrekeningEntity.id}</dd>
          <dt>
            <span id="naam">Naam</span>
          </dt>
          <dd>{subrekeningEntity.naam}</dd>
          <dt>
            <span id="nummer">Nummer</span>
          </dt>
          <dd>{subrekeningEntity.nummer}</dd>
          <dt>
            <span id="omschrijving">Omschrijving</span>
          </dt>
          <dd>{subrekeningEntity.omschrijving}</dd>
          <dt>Heeft Hoofdrekening</dt>
          <dd>{subrekeningEntity.heeftHoofdrekening ? subrekeningEntity.heeftHoofdrekening.id : ''}</dd>
          <dt>Heeft Kostenplaats</dt>
          <dd>{subrekeningEntity.heeftKostenplaats ? subrekeningEntity.heeftKostenplaats.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/subrekening" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/subrekening/${subrekeningEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default SubrekeningDetail;
