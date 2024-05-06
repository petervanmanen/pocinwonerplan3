import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IClientbegeleider } from 'app/shared/model/clientbegeleider.model';
import { getEntities as getClientbegeleiders } from 'app/entities/clientbegeleider/clientbegeleider.reducer';
import { IParticipatiedossier } from 'app/shared/model/participatiedossier.model';
import { getEntity, updateEntity, createEntity, reset } from './participatiedossier.reducer';

export const ParticipatiedossierUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const clientbegeleiders = useAppSelector(state => state.clientbegeleider.entities);
  const participatiedossierEntity = useAppSelector(state => state.participatiedossier.entity);
  const loading = useAppSelector(state => state.participatiedossier.loading);
  const updating = useAppSelector(state => state.participatiedossier.updating);
  const updateSuccess = useAppSelector(state => state.participatiedossier.updateSuccess);

  const handleClose = () => {
    navigate('/participatiedossier');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getClientbegeleiders({}));
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
      ...participatiedossierEntity,
      ...values,
      emptyClientbegeleider: clientbegeleiders.find(it => it.id.toString() === values.emptyClientbegeleider?.toString()),
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
          ...participatiedossierEntity,
          emptyClientbegeleider: participatiedossierEntity?.emptyClientbegeleider?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.participatiedossier.home.createOrEditLabel" data-cy="ParticipatiedossierCreateUpdateHeading">
            Create or edit a Participatiedossier
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
                <ValidatedField name="id" required readOnly id="participatiedossier-id" label="ID" validate={{ required: true }} />
              ) : null}
              <ValidatedField
                label="Arbeidsvermogen"
                id="participatiedossier-arbeidsvermogen"
                name="arbeidsvermogen"
                data-cy="arbeidsvermogen"
                type="text"
                validate={{
                  maxLength: { value: 100, message: 'This field cannot be longer than 100 characters.' },
                }}
              />
              <ValidatedField
                label="Begeleiderscode"
                id="participatiedossier-begeleiderscode"
                name="begeleiderscode"
                data-cy="begeleiderscode"
                type="text"
              />
              <ValidatedField
                label="Beschutwerk"
                id="participatiedossier-beschutwerk"
                name="beschutwerk"
                data-cy="beschutwerk"
                type="text"
              />
              <ValidatedField
                label="Clientbegeleiderid"
                id="participatiedossier-clientbegeleiderid"
                name="clientbegeleiderid"
                data-cy="clientbegeleiderid"
                type="text"
              />
              <ValidatedField
                label="Datumeindebegeleiding"
                id="participatiedossier-datumeindebegeleiding"
                name="datumeindebegeleiding"
                data-cy="datumeindebegeleiding"
                type="text"
              />
              <ValidatedField
                label="Datumstartbegeleiding"
                id="participatiedossier-datumstartbegeleiding"
                name="datumstartbegeleiding"
                data-cy="datumstartbegeleiding"
                type="text"
              />
              <ValidatedField
                label="Indicatiedoelgroepregister"
                id="participatiedossier-indicatiedoelgroepregister"
                name="indicatiedoelgroepregister"
                data-cy="indicatiedoelgroepregister"
                type="text"
              />
              <ValidatedField
                id="participatiedossier-emptyClientbegeleider"
                name="emptyClientbegeleider"
                data-cy="emptyClientbegeleider"
                label="Empty Clientbegeleider"
                type="select"
                required
              >
                <option value="" key="0" />
                {clientbegeleiders
                  ? clientbegeleiders.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <FormText>This field is required.</FormText>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/participatiedossier" replace color="info">
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

export default ParticipatiedossierUpdate;
