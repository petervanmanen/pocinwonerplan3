import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './inkooporder.reducer';

export const InkooporderDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const inkooporderEntity = useAppSelector(state => state.inkooporder.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="inkooporderDetailsHeading">Inkooporder</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{inkooporderEntity.id}</dd>
          <dt>
            <span id="artikelcode">Artikelcode</span>
          </dt>
          <dd>{inkooporderEntity.artikelcode}</dd>
          <dt>
            <span id="betalingmeerderejaren">Betalingmeerderejaren</span>
          </dt>
          <dd>{inkooporderEntity.betalingmeerderejaren ? 'true' : 'false'}</dd>
          <dt>
            <span id="betreft">Betreft</span>
          </dt>
          <dd>{inkooporderEntity.betreft}</dd>
          <dt>
            <span id="datumeinde">Datumeinde</span>
          </dt>
          <dd>{inkooporderEntity.datumeinde}</dd>
          <dt>
            <span id="datumingediend">Datumingediend</span>
          </dt>
          <dd>{inkooporderEntity.datumingediend}</dd>
          <dt>
            <span id="datumstart">Datumstart</span>
          </dt>
          <dd>{inkooporderEntity.datumstart}</dd>
          <dt>
            <span id="goederencode">Goederencode</span>
          </dt>
          <dd>{inkooporderEntity.goederencode}</dd>
          <dt>
            <span id="omschrijving">Omschrijving</span>
          </dt>
          <dd>{inkooporderEntity.omschrijving}</dd>
          <dt>
            <span id="ordernummer">Ordernummer</span>
          </dt>
          <dd>{inkooporderEntity.ordernummer}</dd>
          <dt>
            <span id="saldo">Saldo</span>
          </dt>
          <dd>{inkooporderEntity.saldo}</dd>
          <dt>
            <span id="totaalnettobedrag">Totaalnettobedrag</span>
          </dt>
          <dd>{inkooporderEntity.totaalnettobedrag}</dd>
          <dt>
            <span id="wijzevanaanbesteden">Wijzevanaanbesteden</span>
          </dt>
          <dd>{inkooporderEntity.wijzevanaanbesteden}</dd>
          <dt>Betreft Contract</dt>
          <dd>{inkooporderEntity.betreftContract ? inkooporderEntity.betreftContract.id : ''}</dd>
          <dt>Oorspronkelijk Inkooporder</dt>
          <dd>{inkooporderEntity.oorspronkelijkInkooporder ? inkooporderEntity.oorspronkelijkInkooporder.id : ''}</dd>
          <dt>Heeft Inkooppakket</dt>
          <dd>{inkooporderEntity.heeftInkooppakket ? inkooporderEntity.heeftInkooppakket.id : ''}</dd>
          <dt>Verplichtingaan Leverancier</dt>
          <dd>{inkooporderEntity.verplichtingaanLeverancier ? inkooporderEntity.verplichtingaanLeverancier.id : ''}</dd>
          <dt>Wordtgeschrevenop Hoofdrekening</dt>
          <dd>
            {inkooporderEntity.wordtgeschrevenopHoofdrekenings
              ? inkooporderEntity.wordtgeschrevenopHoofdrekenings.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.id}</a>
                    {inkooporderEntity.wordtgeschrevenopHoofdrekenings && i === inkooporderEntity.wordtgeschrevenopHoofdrekenings.length - 1
                      ? ''
                      : ', '}
                  </span>
                ))
              : null}
          </dd>
          <dt>Gerelateerd Inkooporder 2</dt>
          <dd>{inkooporderEntity.gerelateerdInkooporder2 ? inkooporderEntity.gerelateerdInkooporder2.id : ''}</dd>
          <dt>Heeft Kostenplaats</dt>
          <dd>
            {inkooporderEntity.heeftKostenplaats
              ? inkooporderEntity.heeftKostenplaats.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.id}</a>
                    {inkooporderEntity.heeftKostenplaats && i === inkooporderEntity.heeftKostenplaats.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
        </dl>
        <Button tag={Link} to="/inkooporder" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/inkooporder/${inkooporderEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default InkooporderDetail;
