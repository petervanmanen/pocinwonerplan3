import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './sbiactiviteit.reducer';

export const SbiactiviteitDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const sbiactiviteitEntity = useAppSelector(state => state.sbiactiviteit.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="sbiactiviteitDetailsHeading">Sbiactiviteit</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{sbiactiviteitEntity.id}</dd>
          <dt>
            <span id="datumeindesbiactiviteit">Datumeindesbiactiviteit</span>
          </dt>
          <dd>
            {sbiactiviteitEntity.datumeindesbiactiviteit ? (
              <TextFormat value={sbiactiviteitEntity.datumeindesbiactiviteit} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="datumingangsbiactiviteit">Datumingangsbiactiviteit</span>
          </dt>
          <dd>
            {sbiactiviteitEntity.datumingangsbiactiviteit ? (
              <TextFormat value={sbiactiviteitEntity.datumingangsbiactiviteit} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="hoofdniveau">Hoofdniveau</span>
          </dt>
          <dd>{sbiactiviteitEntity.hoofdniveau}</dd>
          <dt>
            <span id="hoofdniveauomschrijving">Hoofdniveauomschrijving</span>
          </dt>
          <dd>{sbiactiviteitEntity.hoofdniveauomschrijving}</dd>
          <dt>
            <span id="naamactiviteit">Naamactiviteit</span>
          </dt>
          <dd>{sbiactiviteitEntity.naamactiviteit}</dd>
          <dt>
            <span id="sbicode">Sbicode</span>
          </dt>
          <dd>{sbiactiviteitEntity.sbicode}</dd>
          <dt>
            <span id="sbigroep">Sbigroep</span>
          </dt>
          <dd>{sbiactiviteitEntity.sbigroep}</dd>
          <dt>
            <span id="sbigroepomschrijving">Sbigroepomschrijving</span>
          </dt>
          <dd>{sbiactiviteitEntity.sbigroepomschrijving}</dd>
        </dl>
        <Button tag={Link} to="/sbiactiviteit" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/sbiactiviteit/${sbiactiviteitEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default SbiactiviteitDetail;
