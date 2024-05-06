import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './nietnatuurlijkpersoon.reducer';

export const NietnatuurlijkpersoonDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const nietnatuurlijkpersoonEntity = useAppSelector(state => state.nietnatuurlijkpersoon.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="nietnatuurlijkpersoonDetailsHeading">Nietnatuurlijkpersoon</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{nietnatuurlijkpersoonEntity.id}</dd>
          <dt>
            <span id="datumaanvang">Datumaanvang</span>
          </dt>
          <dd>
            {nietnatuurlijkpersoonEntity.datumaanvang ? (
              <TextFormat value={nietnatuurlijkpersoonEntity.datumaanvang} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="datumeinde">Datumeinde</span>
          </dt>
          <dd>
            {nietnatuurlijkpersoonEntity.datumeinde ? (
              <TextFormat value={nietnatuurlijkpersoonEntity.datumeinde} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="datumuitschrijving">Datumuitschrijving</span>
          </dt>
          <dd>
            {nietnatuurlijkpersoonEntity.datumuitschrijving ? (
              <TextFormat value={nietnatuurlijkpersoonEntity.datumuitschrijving} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="datumvoortzetting">Datumvoortzetting</span>
          </dt>
          <dd>
            {nietnatuurlijkpersoonEntity.datumvoortzetting ? (
              <TextFormat value={nietnatuurlijkpersoonEntity.datumvoortzetting} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="faxnummer">Faxnummer</span>
          </dt>
          <dd>{nietnatuurlijkpersoonEntity.faxnummer}</dd>
          <dt>
            <span id="ingeschreven">Ingeschreven</span>
          </dt>
          <dd>{nietnatuurlijkpersoonEntity.ingeschreven ? 'true' : 'false'}</dd>
          <dt>
            <span id="inoprichting">Inoprichting</span>
          </dt>
          <dd>{nietnatuurlijkpersoonEntity.inoprichting ? 'true' : 'false'}</dd>
          <dt>
            <span id="kvknummer">Kvknummer</span>
          </dt>
          <dd>{nietnatuurlijkpersoonEntity.kvknummer}</dd>
          <dt>
            <span id="nnpid">Nnpid</span>
          </dt>
          <dd>{nietnatuurlijkpersoonEntity.nnpid}</dd>
          <dt>
            <span id="rechtsvorm">Rechtsvorm</span>
          </dt>
          <dd>{nietnatuurlijkpersoonEntity.rechtsvorm}</dd>
          <dt>
            <span id="rsinnummer">Rsinnummer</span>
          </dt>
          <dd>{nietnatuurlijkpersoonEntity.rsinnummer}</dd>
          <dt>
            <span id="statutairenaam">Statutairenaam</span>
          </dt>
          <dd>{nietnatuurlijkpersoonEntity.statutairenaam}</dd>
          <dt>
            <span id="statutairezetel">Statutairezetel</span>
          </dt>
          <dd>{nietnatuurlijkpersoonEntity.statutairezetel}</dd>
          <dt>
            <span id="websiteurl">Websiteurl</span>
          </dt>
          <dd>{nietnatuurlijkpersoonEntity.websiteurl}</dd>
        </dl>
        <Button tag={Link} to="/nietnatuurlijkpersoon" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/nietnatuurlijkpersoon/${nietnatuurlijkpersoonEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default NietnatuurlijkpersoonDetail;
