import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IBeschikking } from 'app/shared/model/beschikking.model';
import { getEntities as getBeschikkings } from 'app/entities/beschikking/beschikking.reducer';
import { IClient } from 'app/shared/model/client.model';
import { getEntities as getClients } from 'app/entities/client/client.reducer';
import { IDeclaratie } from 'app/shared/model/declaratie.model';
import { getEntities as getDeclaraties } from 'app/entities/declaratie/declaratie.reducer';
import { IToewijzing } from 'app/shared/model/toewijzing.model';
import { getEntities as getToewijzings } from 'app/entities/toewijzing/toewijzing.reducer';
import { IDeclaratieregel } from 'app/shared/model/declaratieregel.model';
import { getEntity, updateEntity, createEntity, reset } from './declaratieregel.reducer';

export const DeclaratieregelUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const beschikkings = useAppSelector(state => state.beschikking.entities);
  const clients = useAppSelector(state => state.client.entities);
  const declaraties = useAppSelector(state => state.declaratie.entities);
  const toewijzings = useAppSelector(state => state.toewijzing.entities);
  const declaratieregelEntity = useAppSelector(state => state.declaratieregel.entity);
  const loading = useAppSelector(state => state.declaratieregel.loading);
  const updating = useAppSelector(state => state.declaratieregel.updating);
  const updateSuccess = useAppSelector(state => state.declaratieregel.updateSuccess);

  const handleClose = () => {
    navigate('/declaratieregel');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getBeschikkings({}));
    dispatch(getClients({}));
    dispatch(getDeclaraties({}));
    dispatch(getToewijzings({}));
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
    if (values.bedrag !== undefined && typeof values.bedrag !== 'number') {
      values.bedrag = Number(values.bedrag);
    }

    const entity = {
      ...declaratieregelEntity,
      ...values,
      isvoorBeschikking: beschikkings.find(it => it.id.toString() === values.isvoorBeschikking?.toString()),
      betreftClient: clients.find(it => it.id.toString() === values.betreftClient?.toString()),
      valtbinnenDeclaratie: declaraties.find(it => it.id.toString() === values.valtbinnenDeclaratie?.toString()),
      isopbasisvanToewijzing: toewijzings.find(it => it.id.toString() === values.isopbasisvanToewijzing?.toString()),
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
          ...declaratieregelEntity,
          isvoorBeschikking: declaratieregelEntity?.isvoorBeschikking?.id,
          betreftClient: declaratieregelEntity?.betreftClient?.id,
          valtbinnenDeclaratie: declaratieregelEntity?.valtbinnenDeclaratie?.id,
          isopbasisvanToewijzing: declaratieregelEntity?.isopbasisvanToewijzing?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.declaratieregel.home.createOrEditLabel" data-cy="DeclaratieregelCreateUpdateHeading">
            Create or edit a Declaratieregel
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
                <ValidatedField name="id" required readOnly id="declaratieregel-id" label="ID" validate={{ required: true }} />
              ) : null}
              <ValidatedField label="Bedrag" id="declaratieregel-bedrag" name="bedrag" data-cy="bedrag" type="text" />
              <ValidatedField
                label="Code"
                id="declaratieregel-code"
                name="code"
                data-cy="code"
                type="text"
                validate={{
                  maxLength: { value: 20, message: 'This field cannot be longer than 20 characters.' },
                }}
              />
              <ValidatedField label="Datumeinde" id="declaratieregel-datumeinde" name="datumeinde" data-cy="datumeinde" type="date" />
              <ValidatedField label="Datumstart" id="declaratieregel-datumstart" name="datumstart" data-cy="datumstart" type="date" />
              <ValidatedField
                id="declaratieregel-isvoorBeschikking"
                name="isvoorBeschikking"
                data-cy="isvoorBeschikking"
                label="Isvoor Beschikking"
                type="select"
              >
                <option value="" key="0" />
                {beschikkings
                  ? beschikkings.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="declaratieregel-betreftClient"
                name="betreftClient"
                data-cy="betreftClient"
                label="Betreft Client"
                type="select"
              >
                <option value="" key="0" />
                {clients
                  ? clients.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="declaratieregel-valtbinnenDeclaratie"
                name="valtbinnenDeclaratie"
                data-cy="valtbinnenDeclaratie"
                label="Valtbinnen Declaratie"
                type="select"
              >
                <option value="" key="0" />
                {declaraties
                  ? declaraties.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="declaratieregel-isopbasisvanToewijzing"
                name="isopbasisvanToewijzing"
                data-cy="isopbasisvanToewijzing"
                label="Isopbasisvan Toewijzing"
                type="select"
              >
                <option value="" key="0" />
                {toewijzings
                  ? toewijzings.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/declaratieregel" replace color="info">
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

export default DeclaratieregelUpdate;
