import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './kadastralemutatie.reducer';

export const KadastralemutatieDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const kadastralemutatieEntity = useAppSelector(state => state.kadastralemutatie.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="kadastralemutatieDetailsHeading">Kadastralemutatie</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{kadastralemutatieEntity.id}</dd>
          <dt>Betrokkenen Rechtspersoon</dt>
          <dd>
            {kadastralemutatieEntity.betrokkenenRechtspersoons
              ? kadastralemutatieEntity.betrokkenenRechtspersoons.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.id}</a>
                    {kadastralemutatieEntity.betrokkenenRechtspersoons && i === kadastralemutatieEntity.betrokkenenRechtspersoons.length - 1
                      ? ''
                      : ', '}
                  </span>
                ))
              : null}
          </dd>
        </dl>
        <Button tag={Link} to="/kadastralemutatie" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/kadastralemutatie/${kadastralemutatieEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default KadastralemutatieDetail;
