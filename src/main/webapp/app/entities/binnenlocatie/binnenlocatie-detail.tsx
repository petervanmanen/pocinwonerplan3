import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './binnenlocatie.reducer';

export const BinnenlocatieDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const binnenlocatieEntity = useAppSelector(state => state.binnenlocatie.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="binnenlocatieDetailsHeading">Binnenlocatie</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{binnenlocatieEntity.id}</dd>
          <dt>
            <span id="adres">Adres</span>
          </dt>
          <dd>{binnenlocatieEntity.adres}</dd>
          <dt>
            <span id="bouwjaar">Bouwjaar</span>
          </dt>
          <dd>{binnenlocatieEntity.bouwjaar}</dd>
          <dt>
            <span id="gemeentelijk">Gemeentelijk</span>
          </dt>
          <dd>{binnenlocatieEntity.gemeentelijk ? 'true' : 'false'}</dd>
          <dt>
            <span id="geschattekostenperjaar">Geschattekostenperjaar</span>
          </dt>
          <dd>{binnenlocatieEntity.geschattekostenperjaar}</dd>
          <dt>
            <span id="gymzaal">Gymzaal</span>
          </dt>
          <dd>{binnenlocatieEntity.gymzaal}</dd>
          <dt>
            <span id="klokurenonderwijs">Klokurenonderwijs</span>
          </dt>
          <dd>{binnenlocatieEntity.klokurenonderwijs}</dd>
          <dt>
            <span id="klokurenverenigingen">Klokurenverenigingen</span>
          </dt>
          <dd>{binnenlocatieEntity.klokurenverenigingen}</dd>
          <dt>
            <span id="locatie">Locatie</span>
          </dt>
          <dd>{binnenlocatieEntity.locatie}</dd>
          <dt>
            <span id="onderhoudsniveau">Onderhoudsniveau</span>
          </dt>
          <dd>{binnenlocatieEntity.onderhoudsniveau}</dd>
          <dt>
            <span id="onderhoudsstatus">Onderhoudsstatus</span>
          </dt>
          <dd>{binnenlocatieEntity.onderhoudsstatus}</dd>
          <dt>
            <span id="sporthal">Sporthal</span>
          </dt>
          <dd>{binnenlocatieEntity.sporthal}</dd>
          <dt>
            <span id="vloeroppervlakte">Vloeroppervlakte</span>
          </dt>
          <dd>{binnenlocatieEntity.vloeroppervlakte}</dd>
          <dt>Isgevestigdin Verblijfsobject</dt>
          <dd>{binnenlocatieEntity.isgevestigdinVerblijfsobject ? binnenlocatieEntity.isgevestigdinVerblijfsobject.id : ''}</dd>
          <dt>Bedient Wijk</dt>
          <dd>{binnenlocatieEntity.bedientWijk ? binnenlocatieEntity.bedientWijk.id : ''}</dd>
          <dt>Heeft Belijning</dt>
          <dd>
            {binnenlocatieEntity.heeftBelijnings
              ? binnenlocatieEntity.heeftBelijnings.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.id}</a>
                    {binnenlocatieEntity.heeftBelijnings && i === binnenlocatieEntity.heeftBelijnings.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
          <dt>Heeft Sportmateriaal</dt>
          <dd>
            {binnenlocatieEntity.heeftSportmateriaals
              ? binnenlocatieEntity.heeftSportmateriaals.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.id}</a>
                    {binnenlocatieEntity.heeftSportmateriaals && i === binnenlocatieEntity.heeftSportmateriaals.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
        </dl>
        <Button tag={Link} to="/binnenlocatie" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/binnenlocatie/${binnenlocatieEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default BinnenlocatieDetail;
