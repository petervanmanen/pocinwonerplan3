import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IApplicatie } from 'app/shared/model/applicatie.model';
import { getEntities as getApplicaties } from 'app/entities/applicatie/applicatie.reducer';
import { IVersie } from 'app/shared/model/versie.model';
import { getEntity, updateEntity, createEntity, reset } from './versie.reducer';

export const VersieUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const applicaties = useAppSelector(state => state.applicatie.entities);
  const versieEntity = useAppSelector(state => state.versie.entity);
  const loading = useAppSelector(state => state.versie.loading);
  const updating = useAppSelector(state => state.versie.updating);
  const updateSuccess = useAppSelector(state => state.versie.updateSuccess);

  const handleClose = () => {
    navigate('/versie');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getApplicaties({}));
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
    if (values.kosten !== undefined && typeof values.kosten !== 'number') {
      values.kosten = Number(values.kosten);
    }

    const entity = {
      ...versieEntity,
      ...values,
      heeftversiesApplicatie: applicaties.find(it => it.id.toString() === values.heeftversiesApplicatie?.toString()),
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
          ...versieEntity,
          heeftversiesApplicatie: versieEntity?.heeftversiesApplicatie?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.versie.home.createOrEditLabel" data-cy="VersieCreateUpdateHeading">
            Create or edit a Versie
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="versie-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField label="Aantal" id="versie-aantal" name="aantal" data-cy="aantal" type="text" />
              <ValidatedField
                label="Datumeindesupport"
                id="versie-datumeindesupport"
                name="datumeindesupport"
                data-cy="datumeindesupport"
                type="date"
              />
              <ValidatedField label="Kosten" id="versie-kosten" name="kosten" data-cy="kosten" type="text" />
              <ValidatedField label="Licentie" id="versie-licentie" name="licentie" data-cy="licentie" type="text" />
              <ValidatedField label="Status" id="versie-status" name="status" data-cy="status" type="text" />
              <ValidatedField
                label="Versienummer"
                id="versie-versienummer"
                name="versienummer"
                data-cy="versienummer"
                type="text"
                validate={{
                  maxLength: { value: 8, message: 'This field cannot be longer than 8 characters.' },
                }}
              />
              <ValidatedField
                id="versie-heeftversiesApplicatie"
                name="heeftversiesApplicatie"
                data-cy="heeftversiesApplicatie"
                label="Heeftversies Applicatie"
                type="select"
                required
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
              <FormText>This field is required.</FormText>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/versie" replace color="info">
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

export default VersieUpdate;
