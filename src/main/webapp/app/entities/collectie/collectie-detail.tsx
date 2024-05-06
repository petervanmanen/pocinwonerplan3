import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './collectie.reducer';

export const CollectieDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const collectieEntity = useAppSelector(state => state.collectie.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="collectieDetailsHeading">Collectie</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{collectieEntity.id}</dd>
          <dt>
            <span id="naam">Naam</span>
          </dt>
          <dd>{collectieEntity.naam}</dd>
          <dt>
            <span id="omschrijving">Omschrijving</span>
          </dt>
          <dd>{collectieEntity.omschrijving}</dd>
          <dt>Bevat Museumobject</dt>
          <dd>
            {collectieEntity.bevatMuseumobjects
              ? collectieEntity.bevatMuseumobjects.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.id}</a>
                    {collectieEntity.bevatMuseumobjects && i === collectieEntity.bevatMuseumobjects.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
        </dl>
        <Button tag={Link} to="/collectie" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/collectie/${collectieEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default CollectieDetail;
