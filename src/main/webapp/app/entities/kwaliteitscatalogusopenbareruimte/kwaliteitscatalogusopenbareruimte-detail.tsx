import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './kwaliteitscatalogusopenbareruimte.reducer';

export const KwaliteitscatalogusopenbareruimteDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const kwaliteitscatalogusopenbareruimteEntity = useAppSelector(state => state.kwaliteitscatalogusopenbareruimte.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="kwaliteitscatalogusopenbareruimteDetailsHeading">Kwaliteitscatalogusopenbareruimte</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{kwaliteitscatalogusopenbareruimteEntity.id}</dd>
        </dl>
        <Button tag={Link} to="/kwaliteitscatalogusopenbareruimte" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button
          tag={Link}
          to={`/kwaliteitscatalogusopenbareruimte/${kwaliteitscatalogusopenbareruimteEntity.id}/edit`}
          replace
          color="primary"
        >
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default KwaliteitscatalogusopenbareruimteDetail;
