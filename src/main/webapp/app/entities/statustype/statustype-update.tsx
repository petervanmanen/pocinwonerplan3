import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IZaaktype } from 'app/shared/model/zaaktype.model';
import { getEntities as getZaaktypes } from 'app/entities/zaaktype/zaaktype.reducer';
import { IStatustype } from 'app/shared/model/statustype.model';
import { getEntity, updateEntity, createEntity, reset } from './statustype.reducer';

export const StatustypeUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const zaaktypes = useAppSelector(state => state.zaaktype.entities);
  const statustypeEntity = useAppSelector(state => state.statustype.entity);
  const loading = useAppSelector(state => state.statustype.loading);
  const updating = useAppSelector(state => state.statustype.updating);
  const updateSuccess = useAppSelector(state => state.statustype.updateSuccess);

  const handleClose = () => {
    navigate('/statustype');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getZaaktypes({}));
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
      ...statustypeEntity,
      ...values,
      heeftZaaktype: zaaktypes.find(it => it.id.toString() === values.heeftZaaktype?.toString()),
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
          ...statustypeEntity,
          heeftZaaktype: statustypeEntity?.heeftZaaktype?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.statustype.home.createOrEditLabel" data-cy="StatustypeCreateUpdateHeading">
            Create or edit a Statustype
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="statustype-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField
                label="Datumbegingeldigheidstatustype"
                id="statustype-datumbegingeldigheidstatustype"
                name="datumbegingeldigheidstatustype"
                data-cy="datumbegingeldigheidstatustype"
                type="text"
              />
              <ValidatedField
                label="Datumeindegeldigheidstatustype"
                id="statustype-datumeindegeldigheidstatustype"
                name="datumeindegeldigheidstatustype"
                data-cy="datumeindegeldigheidstatustype"
                type="text"
              />
              <ValidatedField
                label="Doorlooptijdstatus"
                id="statustype-doorlooptijdstatus"
                name="doorlooptijdstatus"
                data-cy="doorlooptijdstatus"
                type="text"
              />
              <ValidatedField
                label="Statustypeomschrijving"
                id="statustype-statustypeomschrijving"
                name="statustypeomschrijving"
                data-cy="statustypeomschrijving"
                type="text"
              />
              <ValidatedField
                label="Statustypeomschrijvinggeneriek"
                id="statustype-statustypeomschrijvinggeneriek"
                name="statustypeomschrijvinggeneriek"
                data-cy="statustypeomschrijvinggeneriek"
                type="text"
              />
              <ValidatedField
                label="Statustypevolgnummer"
                id="statustype-statustypevolgnummer"
                name="statustypevolgnummer"
                data-cy="statustypevolgnummer"
                type="text"
              />
              <ValidatedField
                id="statustype-heeftZaaktype"
                name="heeftZaaktype"
                data-cy="heeftZaaktype"
                label="Heeft Zaaktype"
                type="select"
                required
              >
                <option value="" key="0" />
                {zaaktypes
                  ? zaaktypes.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <FormText>This field is required.</FormText>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/statustype" replace color="info">
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

export default StatustypeUpdate;
