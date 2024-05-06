import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ILeverancier } from 'app/shared/model/leverancier.model';
import { getEntities as getLeveranciers } from 'app/entities/leverancier/leverancier.reducer';
import { IApplicatie } from 'app/shared/model/applicatie.model';
import { getEntities as getApplicaties } from 'app/entities/applicatie/applicatie.reducer';
import { IZaak } from 'app/shared/model/zaak.model';
import { getEntities as getZaaks } from 'app/entities/zaak/zaak.reducer';
import { IMedewerker } from 'app/shared/model/medewerker.model';
import { getEntity, updateEntity, createEntity, reset } from './medewerker.reducer';

export const MedewerkerUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const leveranciers = useAppSelector(state => state.leverancier.entities);
  const applicaties = useAppSelector(state => state.applicatie.entities);
  const zaaks = useAppSelector(state => state.zaak.entities);
  const medewerkerEntity = useAppSelector(state => state.medewerker.entity);
  const loading = useAppSelector(state => state.medewerker.loading);
  const updating = useAppSelector(state => state.medewerker.updating);
  const updateSuccess = useAppSelector(state => state.medewerker.updateSuccess);

  const handleClose = () => {
    navigate('/medewerker');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getLeveranciers({}));
    dispatch(getApplicaties({}));
    dispatch(getZaaks({}));
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
      ...medewerkerEntity,
      ...values,
      geleverdviaLeverancier: leveranciers.find(it => it.id.toString() === values.geleverdviaLeverancier?.toString()),
      rollenApplicaties: mapIdList(values.rollenApplicaties),
      afhandelendmedewerkerZaaks: mapIdList(values.afhandelendmedewerkerZaaks),
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
          ...medewerkerEntity,
          geleverdviaLeverancier: medewerkerEntity?.geleverdviaLeverancier?.id,
          rollenApplicaties: medewerkerEntity?.rollenApplicaties?.map(e => e.id.toString()),
          afhandelendmedewerkerZaaks: medewerkerEntity?.afhandelendmedewerkerZaaks?.map(e => e.id.toString()),
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.medewerker.home.createOrEditLabel" data-cy="MedewerkerCreateUpdateHeading">
            Create or edit a Medewerker
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="medewerker-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField label="Achternaam" id="medewerker-achternaam" name="achternaam" data-cy="achternaam" type="text" />
              <ValidatedField
                label="Datumindienst"
                id="medewerker-datumindienst"
                name="datumindienst"
                data-cy="datumindienst"
                type="date"
              />
              <ValidatedField
                label="Datumuitdienst"
                id="medewerker-datumuitdienst"
                name="datumuitdienst"
                data-cy="datumuitdienst"
                type="text"
              />
              <ValidatedField label="Emailadres" id="medewerker-emailadres" name="emailadres" data-cy="emailadres" type="text" />
              <ValidatedField label="Extern" id="medewerker-extern" name="extern" data-cy="extern" type="text" />
              <ValidatedField label="Functie" id="medewerker-functie" name="functie" data-cy="functie" type="text" />
              <ValidatedField
                label="Geslachtsaanduiding"
                id="medewerker-geslachtsaanduiding"
                name="geslachtsaanduiding"
                data-cy="geslachtsaanduiding"
                type="text"
              />
              <ValidatedField
                label="Medewerkeridentificatie"
                id="medewerker-medewerkeridentificatie"
                name="medewerkeridentificatie"
                data-cy="medewerkeridentificatie"
                type="text"
              />
              <ValidatedField
                label="Medewerkertoelichting"
                id="medewerker-medewerkertoelichting"
                name="medewerkertoelichting"
                data-cy="medewerkertoelichting"
                type="text"
              />
              <ValidatedField label="Roepnaam" id="medewerker-roepnaam" name="roepnaam" data-cy="roepnaam" type="text" />
              <ValidatedField
                label="Telefoonnummer"
                id="medewerker-telefoonnummer"
                name="telefoonnummer"
                data-cy="telefoonnummer"
                type="text"
                validate={{
                  maxLength: { value: 20, message: 'This field cannot be longer than 20 characters.' },
                }}
              />
              <ValidatedField
                label="Voorletters"
                id="medewerker-voorletters"
                name="voorletters"
                data-cy="voorletters"
                type="text"
                validate={{
                  maxLength: { value: 20, message: 'This field cannot be longer than 20 characters.' },
                }}
              />
              <ValidatedField
                label="Voorvoegselachternaam"
                id="medewerker-voorvoegselachternaam"
                name="voorvoegselachternaam"
                data-cy="voorvoegselachternaam"
                type="text"
                validate={{
                  maxLength: { value: 10, message: 'This field cannot be longer than 10 characters.' },
                }}
              />
              <ValidatedField
                id="medewerker-geleverdviaLeverancier"
                name="geleverdviaLeverancier"
                data-cy="geleverdviaLeverancier"
                label="Geleverdvia Leverancier"
                type="select"
              >
                <option value="" key="0" />
                {leveranciers
                  ? leveranciers.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                label="Rollen Applicatie"
                id="medewerker-rollenApplicatie"
                data-cy="rollenApplicatie"
                type="select"
                multiple
                name="rollenApplicaties"
              >
                <option value="" key="0" />
                {applicaties
                  ? applicaties.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                label="Afhandelendmedewerker Zaak"
                id="medewerker-afhandelendmedewerkerZaak"
                data-cy="afhandelendmedewerkerZaak"
                type="select"
                multiple
                name="afhandelendmedewerkerZaaks"
              >
                <option value="" key="0" />
                {zaaks
                  ? zaaks.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/medewerker" replace color="info">
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

export default MedewerkerUpdate;
