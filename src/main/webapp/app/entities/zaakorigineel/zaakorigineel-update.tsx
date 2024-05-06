import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IZaakorigineel } from 'app/shared/model/zaakorigineel.model';
import { getEntity, updateEntity, createEntity, reset } from './zaakorigineel.reducer';

export const ZaakorigineelUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const zaakorigineelEntity = useAppSelector(state => state.zaakorigineel.entity);
  const loading = useAppSelector(state => state.zaakorigineel.loading);
  const updating = useAppSelector(state => state.zaakorigineel.updating);
  const updateSuccess = useAppSelector(state => state.zaakorigineel.updateSuccess);

  const handleClose = () => {
    navigate('/zaakorigineel');
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
      ...zaakorigineelEntity,
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
          ...zaakorigineelEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.zaakorigineel.home.createOrEditLabel" data-cy="ZaakorigineelCreateUpdateHeading">
            Create or edit a Zaakorigineel
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
                <ValidatedField name="id" required readOnly id="zaakorigineel-id" label="ID" validate={{ required: true }} />
              ) : null}
              <ValidatedField
                label="Anderzaakobject"
                id="zaakorigineel-anderzaakobject"
                name="anderzaakobject"
                data-cy="anderzaakobject"
                type="text"
              />
              <ValidatedField
                label="Archiefnominatie"
                id="zaakorigineel-archiefnominatie"
                name="archiefnominatie"
                data-cy="archiefnominatie"
                type="text"
              />
              <ValidatedField label="Datumeinde" id="zaakorigineel-datumeinde" name="datumeinde" data-cy="datumeinde" type="text" />
              <ValidatedField
                label="Datumeindegepland"
                id="zaakorigineel-datumeindegepland"
                name="datumeindegepland"
                data-cy="datumeindegepland"
                type="text"
              />
              <ValidatedField
                label="Datumeindeuiterlijkeafdoening"
                id="zaakorigineel-datumeindeuiterlijkeafdoening"
                name="datumeindeuiterlijkeafdoening"
                data-cy="datumeindeuiterlijkeafdoening"
                type="text"
              />
              <ValidatedField
                label="Datumlaatstebetaling"
                id="zaakorigineel-datumlaatstebetaling"
                name="datumlaatstebetaling"
                data-cy="datumlaatstebetaling"
                type="text"
              />
              <ValidatedField
                label="Datumpublicatie"
                id="zaakorigineel-datumpublicatie"
                name="datumpublicatie"
                data-cy="datumpublicatie"
                type="text"
              />
              <ValidatedField
                label="Datumregistratie"
                id="zaakorigineel-datumregistratie"
                name="datumregistratie"
                data-cy="datumregistratie"
                type="text"
              />
              <ValidatedField label="Datumstart" id="zaakorigineel-datumstart" name="datumstart" data-cy="datumstart" type="text" />
              <ValidatedField
                label="Datumvernietigingdossier"
                id="zaakorigineel-datumvernietigingdossier"
                name="datumvernietigingdossier"
                data-cy="datumvernietigingdossier"
                type="text"
              />
              <ValidatedField
                label="Indicatiebetaling"
                id="zaakorigineel-indicatiebetaling"
                name="indicatiebetaling"
                data-cy="indicatiebetaling"
                type="text"
              />
              <ValidatedField
                label="Indicatiedeelzaken"
                id="zaakorigineel-indicatiedeelzaken"
                name="indicatiedeelzaken"
                data-cy="indicatiedeelzaken"
                type="text"
              />
              <ValidatedField label="Kenmerk" id="zaakorigineel-kenmerk" name="kenmerk" data-cy="kenmerk" type="text" />
              <ValidatedField label="Omschrijving" id="zaakorigineel-omschrijving" name="omschrijving" data-cy="omschrijving" type="text" />
              <ValidatedField
                label="Omschrijvingresultaat"
                id="zaakorigineel-omschrijvingresultaat"
                name="omschrijvingresultaat"
                data-cy="omschrijvingresultaat"
                type="text"
              />
              <ValidatedField label="Opschorting" id="zaakorigineel-opschorting" name="opschorting" data-cy="opschorting" type="text" />
              <ValidatedField label="Toelichting" id="zaakorigineel-toelichting" name="toelichting" data-cy="toelichting" type="text" />
              <ValidatedField
                label="Toelichtingresultaat"
                id="zaakorigineel-toelichtingresultaat"
                name="toelichtingresultaat"
                data-cy="toelichtingresultaat"
                type="text"
              />
              <ValidatedField label="Verlenging" id="zaakorigineel-verlenging" name="verlenging" data-cy="verlenging" type="text" />
              <ValidatedField
                label="Zaakidentificatie"
                id="zaakorigineel-zaakidentificatie"
                name="zaakidentificatie"
                data-cy="zaakidentificatie"
                type="text"
              />
              <ValidatedField label="Zaakniveau" id="zaakorigineel-zaakniveau" name="zaakniveau" data-cy="zaakniveau" type="text" />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/zaakorigineel" replace color="info">
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

export default ZaakorigineelUpdate;
