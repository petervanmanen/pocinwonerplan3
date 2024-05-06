import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IKlantcontact } from 'app/shared/model/klantcontact.model';
import { getEntities as getKlantcontacts } from 'app/entities/klantcontact/klantcontact.reducer';
import { IAfspraakstatus } from 'app/shared/model/afspraakstatus.model';
import { getEntities as getAfspraakstatuses } from 'app/entities/afspraakstatus/afspraakstatus.reducer';
import { IMedewerker } from 'app/shared/model/medewerker.model';
import { getEntities as getMedewerkers } from 'app/entities/medewerker/medewerker.reducer';
import { IZaak } from 'app/shared/model/zaak.model';
import { getEntities as getZaaks } from 'app/entities/zaak/zaak.reducer';
import { IBalieafspraak } from 'app/shared/model/balieafspraak.model';
import { getEntity, updateEntity, createEntity, reset } from './balieafspraak.reducer';

export const BalieafspraakUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const klantcontacts = useAppSelector(state => state.klantcontact.entities);
  const afspraakstatuses = useAppSelector(state => state.afspraakstatus.entities);
  const medewerkers = useAppSelector(state => state.medewerker.entities);
  const zaaks = useAppSelector(state => state.zaak.entities);
  const balieafspraakEntity = useAppSelector(state => state.balieafspraak.entity);
  const loading = useAppSelector(state => state.balieafspraak.loading);
  const updating = useAppSelector(state => state.balieafspraak.updating);
  const updateSuccess = useAppSelector(state => state.balieafspraak.updateSuccess);

  const handleClose = () => {
    navigate('/balieafspraak');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getKlantcontacts({}));
    dispatch(getAfspraakstatuses({}));
    dispatch(getMedewerkers({}));
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
      ...balieafspraakEntity,
      ...values,
      mondtuitinKlantcontact: klantcontacts.find(it => it.id.toString() === values.mondtuitinKlantcontact?.toString()),
      heeftAfspraakstatus: afspraakstatuses.find(it => it.id.toString() === values.heeftAfspraakstatus?.toString()),
      metMedewerker: medewerkers.find(it => it.id.toString() === values.metMedewerker?.toString()),
      heeftbetrekkingopZaak: zaaks.find(it => it.id.toString() === values.heeftbetrekkingopZaak?.toString()),
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
          ...balieafspraakEntity,
          mondtuitinKlantcontact: balieafspraakEntity?.mondtuitinKlantcontact?.id,
          heeftAfspraakstatus: balieafspraakEntity?.heeftAfspraakstatus?.id,
          metMedewerker: balieafspraakEntity?.metMedewerker?.id,
          heeftbetrekkingopZaak: balieafspraakEntity?.heeftbetrekkingopZaak?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.balieafspraak.home.createOrEditLabel" data-cy="BalieafspraakCreateUpdateHeading">
            Create or edit a Balieafspraak
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
                <ValidatedField name="id" required readOnly id="balieafspraak-id" label="ID" validate={{ required: true }} />
              ) : null}
              <ValidatedField
                label="Eindtijdgepland"
                id="balieafspraak-eindtijdgepland"
                name="eindtijdgepland"
                data-cy="eindtijdgepland"
                type="text"
              />
              <ValidatedField label="Notitie" id="balieafspraak-notitie" name="notitie" data-cy="notitie" type="text" />
              <ValidatedField
                label="Starttijdgepland"
                id="balieafspraak-starttijdgepland"
                name="starttijdgepland"
                data-cy="starttijdgepland"
                type="text"
              />
              <ValidatedField
                label="Tijdaangemaakt"
                id="balieafspraak-tijdaangemaakt"
                name="tijdaangemaakt"
                data-cy="tijdaangemaakt"
                type="text"
              />
              <ValidatedField
                label="Tijdsduurgepland"
                id="balieafspraak-tijdsduurgepland"
                name="tijdsduurgepland"
                data-cy="tijdsduurgepland"
                type="text"
              />
              <ValidatedField label="Toelichting" id="balieafspraak-toelichting" name="toelichting" data-cy="toelichting" type="text" />
              <ValidatedField
                label="Wachttijdnastartafspraak"
                id="balieafspraak-wachttijdnastartafspraak"
                name="wachttijdnastartafspraak"
                data-cy="wachttijdnastartafspraak"
                type="text"
              />
              <ValidatedField
                label="Wachttijdtotaal"
                id="balieafspraak-wachttijdtotaal"
                name="wachttijdtotaal"
                data-cy="wachttijdtotaal"
                type="text"
              />
              <ValidatedField
                label="Wachttijdvoorstartafspraak"
                id="balieafspraak-wachttijdvoorstartafspraak"
                name="wachttijdvoorstartafspraak"
                data-cy="wachttijdvoorstartafspraak"
                type="text"
              />
              <ValidatedField
                label="Werkelijketijdsduur"
                id="balieafspraak-werkelijketijdsduur"
                name="werkelijketijdsduur"
                data-cy="werkelijketijdsduur"
                type="text"
              />
              <ValidatedField
                id="balieafspraak-mondtuitinKlantcontact"
                name="mondtuitinKlantcontact"
                data-cy="mondtuitinKlantcontact"
                label="Mondtuitin Klantcontact"
                type="select"
              >
                <option value="" key="0" />
                {klantcontacts
                  ? klantcontacts.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="balieafspraak-heeftAfspraakstatus"
                name="heeftAfspraakstatus"
                data-cy="heeftAfspraakstatus"
                label="Heeft Afspraakstatus"
                type="select"
              >
                <option value="" key="0" />
                {afspraakstatuses
                  ? afspraakstatuses.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="balieafspraak-metMedewerker"
                name="metMedewerker"
                data-cy="metMedewerker"
                label="Met Medewerker"
                type="select"
              >
                <option value="" key="0" />
                {medewerkers
                  ? medewerkers.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="balieafspraak-heeftbetrekkingopZaak"
                name="heeftbetrekkingopZaak"
                data-cy="heeftbetrekkingopZaak"
                label="Heeftbetrekkingop Zaak"
                type="select"
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
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/balieafspraak" replace color="info">
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

export default BalieafspraakUpdate;
