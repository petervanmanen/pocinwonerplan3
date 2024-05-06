import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IBetrokkene } from 'app/shared/model/betrokkene.model';
import { getEntities as getBetrokkenes } from 'app/entities/betrokkene/betrokkene.reducer';
import { IZaak } from 'app/shared/model/zaak.model';
import { getEntities as getZaaks } from 'app/entities/zaak/zaak.reducer';
import { IMedewerker } from 'app/shared/model/medewerker.model';
import { getEntities as getMedewerkers } from 'app/entities/medewerker/medewerker.reducer';
import { ITelefoononderwerp } from 'app/shared/model/telefoononderwerp.model';
import { getEntities as getTelefoononderwerps } from 'app/entities/telefoononderwerp/telefoononderwerp.reducer';
import { ITelefoontje } from 'app/shared/model/telefoontje.model';
import { getEntities as getTelefoontjes } from 'app/entities/telefoontje/telefoontje.reducer';
import { IKlantcontact } from 'app/shared/model/klantcontact.model';
import { getEntity, updateEntity, createEntity, reset } from './klantcontact.reducer';

export const KlantcontactUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const betrokkenes = useAppSelector(state => state.betrokkene.entities);
  const zaaks = useAppSelector(state => state.zaak.entities);
  const medewerkers = useAppSelector(state => state.medewerker.entities);
  const telefoononderwerps = useAppSelector(state => state.telefoononderwerp.entities);
  const telefoontjes = useAppSelector(state => state.telefoontje.entities);
  const klantcontactEntity = useAppSelector(state => state.klantcontact.entity);
  const loading = useAppSelector(state => state.klantcontact.loading);
  const updating = useAppSelector(state => state.klantcontact.updating);
  const updateSuccess = useAppSelector(state => state.klantcontact.updateSuccess);

  const handleClose = () => {
    navigate('/klantcontact');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getBetrokkenes({}));
    dispatch(getZaaks({}));
    dispatch(getMedewerkers({}));
    dispatch(getTelefoononderwerps({}));
    dispatch(getTelefoontjes({}));
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
      ...klantcontactEntity,
      ...values,
      heeftklantcontactenBetrokkene: betrokkenes.find(it => it.id.toString() === values.heeftklantcontactenBetrokkene?.toString()),
      heeftbetrekkingopZaak: zaaks.find(it => it.id.toString() === values.heeftbetrekkingopZaak?.toString()),
      isgevoerddoorMedewerker: medewerkers.find(it => it.id.toString() === values.isgevoerddoorMedewerker?.toString()),
      heeftTelefoononderwerp: telefoononderwerps.find(it => it.id.toString() === values.heeftTelefoononderwerp?.toString()),
      mondtuitinTelefoontje: telefoontjes.find(it => it.id.toString() === values.mondtuitinTelefoontje?.toString()),
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
          ...klantcontactEntity,
          heeftklantcontactenBetrokkene: klantcontactEntity?.heeftklantcontactenBetrokkene?.id,
          heeftbetrekkingopZaak: klantcontactEntity?.heeftbetrekkingopZaak?.id,
          isgevoerddoorMedewerker: klantcontactEntity?.isgevoerddoorMedewerker?.id,
          heeftTelefoononderwerp: klantcontactEntity?.heeftTelefoononderwerp?.id,
          mondtuitinTelefoontje: klantcontactEntity?.mondtuitinTelefoontje?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.klantcontact.home.createOrEditLabel" data-cy="KlantcontactCreateUpdateHeading">
            Create or edit a Klantcontact
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="klantcontact-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField label="Eindtijd" id="klantcontact-eindtijd" name="eindtijd" data-cy="eindtijd" type="text" />
              <ValidatedField
                label="Kanaal"
                id="klantcontact-kanaal"
                name="kanaal"
                data-cy="kanaal"
                type="text"
                validate={{
                  maxLength: { value: 20, message: 'This field cannot be longer than 20 characters.' },
                }}
              />
              <ValidatedField label="Notitie" id="klantcontact-notitie" name="notitie" data-cy="notitie" type="text" />
              <ValidatedField label="Starttijd" id="klantcontact-starttijd" name="starttijd" data-cy="starttijd" type="text" />
              <ValidatedField label="Tijdsduur" id="klantcontact-tijdsduur" name="tijdsduur" data-cy="tijdsduur" type="text" />
              <ValidatedField label="Toelichting" id="klantcontact-toelichting" name="toelichting" data-cy="toelichting" type="text" />
              <ValidatedField
                label="Wachttijdtotaal"
                id="klantcontact-wachttijdtotaal"
                name="wachttijdtotaal"
                data-cy="wachttijdtotaal"
                type="text"
              />
              <ValidatedField
                id="klantcontact-heeftklantcontactenBetrokkene"
                name="heeftklantcontactenBetrokkene"
                data-cy="heeftklantcontactenBetrokkene"
                label="Heeftklantcontacten Betrokkene"
                type="select"
              >
                <option value="" key="0" />
                {betrokkenes
                  ? betrokkenes.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="klantcontact-heeftbetrekkingopZaak"
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
              <ValidatedField
                id="klantcontact-isgevoerddoorMedewerker"
                name="isgevoerddoorMedewerker"
                data-cy="isgevoerddoorMedewerker"
                label="Isgevoerddoor Medewerker"
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
                id="klantcontact-heeftTelefoononderwerp"
                name="heeftTelefoononderwerp"
                data-cy="heeftTelefoononderwerp"
                label="Heeft Telefoononderwerp"
                type="select"
              >
                <option value="" key="0" />
                {telefoononderwerps
                  ? telefoononderwerps.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="klantcontact-mondtuitinTelefoontje"
                name="mondtuitinTelefoontje"
                data-cy="mondtuitinTelefoontje"
                label="Mondtuitin Telefoontje"
                type="select"
              >
                <option value="" key="0" />
                {telefoontjes
                  ? telefoontjes.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/klantcontact" replace color="info">
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

export default KlantcontactUpdate;
