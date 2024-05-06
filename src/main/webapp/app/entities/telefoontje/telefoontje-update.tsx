import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ITelefoonstatus } from 'app/shared/model/telefoonstatus.model';
import { getEntities as getTelefoonstatuses } from 'app/entities/telefoonstatus/telefoonstatus.reducer';
import { ITelefoononderwerp } from 'app/shared/model/telefoononderwerp.model';
import { getEntities as getTelefoononderwerps } from 'app/entities/telefoononderwerp/telefoononderwerp.reducer';
import { ITelefoontje } from 'app/shared/model/telefoontje.model';
import { getEntity, updateEntity, createEntity, reset } from './telefoontje.reducer';

export const TelefoontjeUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const telefoonstatuses = useAppSelector(state => state.telefoonstatus.entities);
  const telefoononderwerps = useAppSelector(state => state.telefoononderwerp.entities);
  const telefoontjeEntity = useAppSelector(state => state.telefoontje.entity);
  const loading = useAppSelector(state => state.telefoontje.loading);
  const updating = useAppSelector(state => state.telefoontje.updating);
  const updateSuccess = useAppSelector(state => state.telefoontje.updateSuccess);

  const handleClose = () => {
    navigate('/telefoontje');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getTelefoonstatuses({}));
    dispatch(getTelefoononderwerps({}));
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
      ...telefoontjeEntity,
      ...values,
      heeftTelefoonstatus: telefoonstatuses.find(it => it.id.toString() === values.heeftTelefoonstatus?.toString()),
      heeftTelefoononderwerp: telefoononderwerps.find(it => it.id.toString() === values.heeftTelefoononderwerp?.toString()),
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
          ...telefoontjeEntity,
          heeftTelefoonstatus: telefoontjeEntity?.heeftTelefoonstatus?.id,
          heeftTelefoononderwerp: telefoontjeEntity?.heeftTelefoononderwerp?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.telefoontje.home.createOrEditLabel" data-cy="TelefoontjeCreateUpdateHeading">
            Create or edit a Telefoontje
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="telefoontje-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField
                label="Afhandeltijdnagesprek"
                id="telefoontje-afhandeltijdnagesprek"
                name="afhandeltijdnagesprek"
                data-cy="afhandeltijdnagesprek"
                type="text"
              />
              <ValidatedField
                label="Deltaisdnconnectie"
                id="telefoontje-deltaisdnconnectie"
                name="deltaisdnconnectie"
                data-cy="deltaisdnconnectie"
                type="text"
              />
              <ValidatedField label="Eindtijd" id="telefoontje-eindtijd" name="eindtijd" data-cy="eindtijd" type="text" />
              <ValidatedField label="Starttijd" id="telefoontje-starttijd" name="starttijd" data-cy="starttijd" type="text" />
              <ValidatedField
                label="Totaleonholdtijd"
                id="telefoontje-totaleonholdtijd"
                name="totaleonholdtijd"
                data-cy="totaleonholdtijd"
                type="text"
              />
              <ValidatedField
                label="Totalespreektijd"
                id="telefoontje-totalespreektijd"
                name="totalespreektijd"
                data-cy="totalespreektijd"
                type="text"
              />
              <ValidatedField
                label="Totalewachttijd"
                id="telefoontje-totalewachttijd"
                name="totalewachttijd"
                data-cy="totalewachttijd"
                type="text"
              />
              <ValidatedField
                label="Totlatetijdsduur"
                id="telefoontje-totlatetijdsduur"
                name="totlatetijdsduur"
                data-cy="totlatetijdsduur"
                type="text"
              />
              <ValidatedField
                label="Trackid"
                id="telefoontje-trackid"
                name="trackid"
                data-cy="trackid"
                type="text"
                validate={{
                  maxLength: { value: 20, message: 'This field cannot be longer than 20 characters.' },
                }}
              />
              <ValidatedField
                id="telefoontje-heeftTelefoonstatus"
                name="heeftTelefoonstatus"
                data-cy="heeftTelefoonstatus"
                label="Heeft Telefoonstatus"
                type="select"
              >
                <option value="" key="0" />
                {telefoonstatuses
                  ? telefoonstatuses.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="telefoontje-heeftTelefoononderwerp"
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
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/telefoontje" replace color="info">
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

export default TelefoontjeUpdate;
