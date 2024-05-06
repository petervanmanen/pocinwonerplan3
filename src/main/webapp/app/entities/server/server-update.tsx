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
import { IServer } from 'app/shared/model/server.model';
import { getEntity, updateEntity, createEntity, reset } from './server.reducer';

export const ServerUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const leveranciers = useAppSelector(state => state.leverancier.entities);
  const serverEntity = useAppSelector(state => state.server.entity);
  const loading = useAppSelector(state => state.server.loading);
  const updating = useAppSelector(state => state.server.updating);
  const updateSuccess = useAppSelector(state => state.server.updateSuccess);

  const handleClose = () => {
    navigate('/server');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getLeveranciers({}));
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
      ...serverEntity,
      ...values,
      heeftleverancierLeverancier: leveranciers.find(it => it.id.toString() === values.heeftleverancierLeverancier?.toString()),
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
          ...serverEntity,
          heeftleverancierLeverancier: serverEntity?.heeftleverancierLeverancier?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.server.home.createOrEditLabel" data-cy="ServerCreateUpdateHeading">
            Create or edit a Server
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="server-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField label="Actief" id="server-actief" name="actief" data-cy="actief" check type="checkbox" />
              <ValidatedField label="Ipadres" id="server-ipadres" name="ipadres" data-cy="ipadres" type="text" />
              <ValidatedField label="Locatie" id="server-locatie" name="locatie" data-cy="locatie" type="text" />
              <ValidatedField label="Organisatie" id="server-organisatie" name="organisatie" data-cy="organisatie" type="text" />
              <ValidatedField label="Serienummer" id="server-serienummer" name="serienummer" data-cy="serienummer" type="text" />
              <ValidatedField
                label="Serverid"
                id="server-serverid"
                name="serverid"
                data-cy="serverid"
                type="text"
                validate={{
                  maxLength: { value: 20, message: 'This field cannot be longer than 20 characters.' },
                }}
              />
              <ValidatedField label="Servertype" id="server-servertype" name="servertype" data-cy="servertype" type="text" />
              <ValidatedField label="Vlan" id="server-vlan" name="vlan" data-cy="vlan" type="text" />
              <ValidatedField
                id="server-heeftleverancierLeverancier"
                name="heeftleverancierLeverancier"
                data-cy="heeftleverancierLeverancier"
                label="Heeftleverancier Leverancier"
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
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/server" replace color="info">
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

export default ServerUpdate;
