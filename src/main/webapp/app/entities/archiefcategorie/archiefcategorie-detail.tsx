import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './archiefcategorie.reducer';

export const ArchiefcategorieDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const archiefcategorieEntity = useAppSelector(state => state.archiefcategorie.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="archiefcategorieDetailsHeading">Archiefcategorie</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{archiefcategorieEntity.id}</dd>
          <dt>
            <span id="naam">Naam</span>
          </dt>
          <dd>{archiefcategorieEntity.naam}</dd>
          <dt>
            <span id="nummer">Nummer</span>
          </dt>
          <dd>{archiefcategorieEntity.nummer}</dd>
          <dt>
            <span id="omschrijving">Omschrijving</span>
          </dt>
          <dd>{archiefcategorieEntity.omschrijving}</dd>
          <dt>Valtbinnen Archief</dt>
          <dd>
            {archiefcategorieEntity.valtbinnenArchiefs
              ? archiefcategorieEntity.valtbinnenArchiefs.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.id}</a>
                    {archiefcategorieEntity.valtbinnenArchiefs && i === archiefcategorieEntity.valtbinnenArchiefs.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
        </dl>
        <Button tag={Link} to="/archiefcategorie" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/archiefcategorie/${archiefcategorieEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default ArchiefcategorieDetail;
