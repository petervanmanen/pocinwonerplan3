import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IIngeschrevenpersoon } from 'app/shared/model/ingeschrevenpersoon.model';
import { getEntity, updateEntity, createEntity, reset } from './ingeschrevenpersoon.reducer';

export const IngeschrevenpersoonUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const ingeschrevenpersoonEntity = useAppSelector(state => state.ingeschrevenpersoon.entity);
  const loading = useAppSelector(state => state.ingeschrevenpersoon.loading);
  const updating = useAppSelector(state => state.ingeschrevenpersoon.updating);
  const updateSuccess = useAppSelector(state => state.ingeschrevenpersoon.updateSuccess);

  const handleClose = () => {
    navigate('/ingeschrevenpersoon');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  // eslint-disable-next-line complexity
  const saveEntity = values => {
    if (values.id !== undefined && typeof values.id !== 'number') {
      values.id = Number(values.id);
    }

    const entity = {
      ...ingeschrevenpersoonEntity,
      ...values,
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {}
      : {
          ...ingeschrevenpersoonEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.ingeschrevenpersoon.home.createOrEditLabel" data-cy="IngeschrevenpersoonCreateUpdateHeading">
            Create or edit a Ingeschrevenpersoon
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? (
                <ValidatedField name="id" required readOnly id="ingeschrevenpersoon-id" label="ID" validate={{ required: true }} />
              ) : null}
              <ValidatedField
                label="Adresherkomst"
                id="ingeschrevenpersoon-adresherkomst"
                name="adresherkomst"
                data-cy="adresherkomst"
                type="text"
              />
              <ValidatedField label="Anummer" id="ingeschrevenpersoon-anummer" name="anummer" data-cy="anummer" type="text" />
              <ValidatedField
                label="Beschrijvinglocatie"
                id="ingeschrevenpersoon-beschrijvinglocatie"
                name="beschrijvinglocatie"
                data-cy="beschrijvinglocatie"
                type="text"
              />
              <ValidatedField
                label="Buitenlandsreisdocument"
                id="ingeschrevenpersoon-buitenlandsreisdocument"
                name="buitenlandsreisdocument"
                data-cy="buitenlandsreisdocument"
                type="text"
              />
              <ValidatedField
                label="Burgerlijkestaat"
                id="ingeschrevenpersoon-burgerlijkestaat"
                name="burgerlijkestaat"
                data-cy="burgerlijkestaat"
                type="text"
              />
              <ValidatedField
                label="Datumbegingeldigheidverblijfplaats"
                id="ingeschrevenpersoon-datumbegingeldigheidverblijfplaats"
                name="datumbegingeldigheidverblijfplaats"
                data-cy="datumbegingeldigheidverblijfplaats"
                type="text"
              />
              <ValidatedField
                label="Datumeindegeldigheidverblijfsplaats"
                id="ingeschrevenpersoon-datumeindegeldigheidverblijfsplaats"
                name="datumeindegeldigheidverblijfsplaats"
                data-cy="datumeindegeldigheidverblijfsplaats"
                type="date"
              />
              <ValidatedField
                label="Datuminschrijvinggemeente"
                id="ingeschrevenpersoon-datuminschrijvinggemeente"
                name="datuminschrijvinggemeente"
                data-cy="datuminschrijvinggemeente"
                type="text"
              />
              <ValidatedField
                label="Datumopschortingbijhouding"
                id="ingeschrevenpersoon-datumopschortingbijhouding"
                name="datumopschortingbijhouding"
                data-cy="datumopschortingbijhouding"
                type="text"
              />
              <ValidatedField
                label="Datumvertrekuitnederland"
                id="ingeschrevenpersoon-datumvertrekuitnederland"
                name="datumvertrekuitnederland"
                data-cy="datumvertrekuitnederland"
                type="text"
              />
              <ValidatedField
                label="Datumvestigingnederland"
                id="ingeschrevenpersoon-datumvestigingnederland"
                name="datumvestigingnederland"
                data-cy="datumvestigingnederland"
                type="text"
              />
              <ValidatedField
                label="Gemeentevaninschrijving"
                id="ingeschrevenpersoon-gemeentevaninschrijving"
                name="gemeentevaninschrijving"
                data-cy="gemeentevaninschrijving"
                type="text"
              />
              <ValidatedField
                label="Gezinsrelatie"
                id="ingeschrevenpersoon-gezinsrelatie"
                name="gezinsrelatie"
                data-cy="gezinsrelatie"
                type="text"
              />
              <ValidatedField
                label="Indicatiegeheim"
                id="ingeschrevenpersoon-indicatiegeheim"
                name="indicatiegeheim"
                data-cy="indicatiegeheim"
                type="text"
              />
              <ValidatedField label="Ingezetene" id="ingeschrevenpersoon-ingezetene" name="ingezetene" data-cy="ingezetene" type="text" />
              <ValidatedField
                label="Landwaarnaarvertrokken"
                id="ingeschrevenpersoon-landwaarnaarvertrokken"
                name="landwaarnaarvertrokken"
                data-cy="landwaarnaarvertrokken"
                type="text"
              />
              <ValidatedField
                label="Landwaarvandaaningeschreven"
                id="ingeschrevenpersoon-landwaarvandaaningeschreven"
                name="landwaarvandaaningeschreven"
                data-cy="landwaarvandaaningeschreven"
                type="text"
              />
              <ValidatedField label="Ouder 1" id="ingeschrevenpersoon-ouder1" name="ouder1" data-cy="ouder1" type="text" />
              <ValidatedField label="Ouder 2" id="ingeschrevenpersoon-ouder2" name="ouder2" data-cy="ouder2" type="text" />
              <ValidatedField label="Partnerid" id="ingeschrevenpersoon-partnerid" name="partnerid" data-cy="partnerid" type="text" />
              <ValidatedField
                label="Redeneindebewoning"
                id="ingeschrevenpersoon-redeneindebewoning"
                name="redeneindebewoning"
                data-cy="redeneindebewoning"
                type="text"
              />
              <ValidatedField
                label="Redenopschortingbijhouding"
                id="ingeschrevenpersoon-redenopschortingbijhouding"
                name="redenopschortingbijhouding"
                data-cy="redenopschortingbijhouding"
                type="text"
              />
              <ValidatedField
                label="Signaleringreisdocument"
                id="ingeschrevenpersoon-signaleringreisdocument"
                name="signaleringreisdocument"
                data-cy="signaleringreisdocument"
                type="text"
              />
              <ValidatedField
                label="Verblijfstitel"
                id="ingeschrevenpersoon-verblijfstitel"
                name="verblijfstitel"
                data-cy="verblijfstitel"
                type="text"
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/ingeschrevenpersoon" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">Back</span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp; Save
              </Button>
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default IngeschrevenpersoonUpdate;
