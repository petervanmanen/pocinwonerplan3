import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './contract.reducer';

export const ContractDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const contractEntity = useAppSelector(state => state.contract.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="contractDetailsHeading">Contract</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{contractEntity.id}</dd>
          <dt>
            <span id="autorisatiegroep">Autorisatiegroep</span>
          </dt>
          <dd>{contractEntity.autorisatiegroep}</dd>
          <dt>
            <span id="beschrijving">Beschrijving</span>
          </dt>
          <dd>{contractEntity.beschrijving}</dd>
          <dt>
            <span id="categorie">Categorie</span>
          </dt>
          <dd>{contractEntity.categorie}</dd>
          <dt>
            <span id="classificatie">Classificatie</span>
          </dt>
          <dd>{contractEntity.classificatie}</dd>
          <dt>
            <span id="contractrevisie">Contractrevisie</span>
          </dt>
          <dd>{contractEntity.contractrevisie}</dd>
          <dt>
            <span id="datumcreatie">Datumcreatie</span>
          </dt>
          <dd>
            {contractEntity.datumcreatie ? (
              <TextFormat value={contractEntity.datumcreatie} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="datumeinde">Datumeinde</span>
          </dt>
          <dd>
            {contractEntity.datumeinde ? <TextFormat value={contractEntity.datumeinde} type="date" format={APP_LOCAL_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="datumstart">Datumstart</span>
          </dt>
          <dd>
            {contractEntity.datumstart ? <TextFormat value={contractEntity.datumstart} type="date" format={APP_LOCAL_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="groep">Groep</span>
          </dt>
          <dd>{contractEntity.groep}</dd>
          <dt>
            <span id="interncontractid">Interncontractid</span>
          </dt>
          <dd>{contractEntity.interncontractid}</dd>
          <dt>
            <span id="interncontractrevisie">Interncontractrevisie</span>
          </dt>
          <dd>{contractEntity.interncontractrevisie}</dd>
          <dt>
            <span id="opmerkingen">Opmerkingen</span>
          </dt>
          <dd>{contractEntity.opmerkingen}</dd>
          <dt>
            <span id="status">Status</span>
          </dt>
          <dd>{contractEntity.status}</dd>
          <dt>
            <span id="type">Type</span>
          </dt>
          <dd>{contractEntity.type}</dd>
          <dt>
            <span id="voorwaarde">Voorwaarde</span>
          </dt>
          <dd>{contractEntity.voorwaarde}</dd>
          <dt>
            <span id="zoekwoorden">Zoekwoorden</span>
          </dt>
          <dd>{contractEntity.zoekwoorden}</dd>
          <dt>Bovenliggend Contract</dt>
          <dd>{contractEntity.bovenliggendContract ? contractEntity.bovenliggendContract.id : ''}</dd>
          <dt>Heeft Leverancier</dt>
          <dd>{contractEntity.heeftLeverancier ? contractEntity.heeftLeverancier.id : ''}</dd>
          <dt>Contractant Leverancier</dt>
          <dd>{contractEntity.contractantLeverancier ? contractEntity.contractantLeverancier.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/contract" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/contract/${contractEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default ContractDetail;
