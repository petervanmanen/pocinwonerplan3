import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IStartformulieraanbesteden } from 'app/shared/model/startformulieraanbesteden.model';
import { getEntity, updateEntity, createEntity, reset } from './startformulieraanbesteden.reducer';

export const StartformulieraanbestedenUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const startformulieraanbestedenEntity = useAppSelector(state => state.startformulieraanbesteden.entity);
  const loading = useAppSelector(state => state.startformulieraanbesteden.loading);
  const updating = useAppSelector(state => state.startformulieraanbesteden.updating);
  const updateSuccess = useAppSelector(state => state.startformulieraanbesteden.updateSuccess);

  const handleClose = () => {
    navigate('/startformulieraanbesteden');
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
    if (values.beoogdetotaleopdrachtwaarde !== undefined && typeof values.beoogdetotaleopdrachtwaarde !== 'number') {
      values.beoogdetotaleopdrachtwaarde = Number(values.beoogdetotaleopdrachtwaarde);
    }

    const entity = {
      ...startformulieraanbestedenEntity,
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
          ...startformulieraanbestedenEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.startformulieraanbesteden.home.createOrEditLabel" data-cy="StartformulieraanbestedenCreateUpdateHeading">
            Create or edit a Startformulieraanbesteden
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
                <ValidatedField name="id" required readOnly id="startformulieraanbesteden-id" label="ID" validate={{ required: true }} />
              ) : null}
              <ValidatedField
                label="Beoogdelooptijd"
                id="startformulieraanbesteden-beoogdelooptijd"
                name="beoogdelooptijd"
                data-cy="beoogdelooptijd"
                type="text"
              />
              <ValidatedField
                label="Beoogdetotaleopdrachtwaarde"
                id="startformulieraanbesteden-beoogdetotaleopdrachtwaarde"
                name="beoogdetotaleopdrachtwaarde"
                data-cy="beoogdetotaleopdrachtwaarde"
                type="text"
              />
              <ValidatedField
                label="Indicatieaanvullendeopdrachtleverancier"
                id="startformulieraanbesteden-indicatieaanvullendeopdrachtleverancier"
                name="indicatieaanvullendeopdrachtleverancier"
                data-cy="indicatieaanvullendeopdrachtleverancier"
                check
                type="checkbox"
              />
              <ValidatedField
                label="Indicatiebeoogdeaanbestedingonderhands"
                id="startformulieraanbesteden-indicatiebeoogdeaanbestedingonderhands"
                name="indicatiebeoogdeaanbestedingonderhands"
                data-cy="indicatiebeoogdeaanbestedingonderhands"
                type="text"
              />
              <ValidatedField
                label="Indicatiebeoogdeprockomtovereen"
                id="startformulieraanbesteden-indicatiebeoogdeprockomtovereen"
                name="indicatiebeoogdeprockomtovereen"
                data-cy="indicatiebeoogdeprockomtovereen"
                check
                type="checkbox"
              />
              <ValidatedField
                label="Indicatieeenmaligelos"
                id="startformulieraanbesteden-indicatieeenmaligelos"
                name="indicatieeenmaligelos"
                data-cy="indicatieeenmaligelos"
                type="text"
              />
              <ValidatedField
                label="Indicatiemeerjarigeraamovereenkomst"
                id="startformulieraanbesteden-indicatiemeerjarigeraamovereenkomst"
                name="indicatiemeerjarigeraamovereenkomst"
                data-cy="indicatiemeerjarigeraamovereenkomst"
                check
                type="checkbox"
              />
              <ValidatedField
                label="Indicatiemeerjarigrepeterend"
                id="startformulieraanbesteden-indicatiemeerjarigrepeterend"
                name="indicatiemeerjarigrepeterend"
                data-cy="indicatiemeerjarigrepeterend"
                type="text"
              />
              <ValidatedField
                label="Indicatoroverkoepelendproject"
                id="startformulieraanbesteden-indicatoroverkoepelendproject"
                name="indicatoroverkoepelendproject"
                data-cy="indicatoroverkoepelendproject"
                type="text"
              />
              <ValidatedField
                label="Omschrijving"
                id="startformulieraanbesteden-omschrijving"
                name="omschrijving"
                data-cy="omschrijving"
                type="text"
              />
              <ValidatedField
                label="Opdrachtcategorie"
                id="startformulieraanbesteden-opdrachtcategorie"
                name="opdrachtcategorie"
                data-cy="opdrachtcategorie"
                type="text"
              />
              <ValidatedField
                label="Opdrachtsoort"
                id="startformulieraanbesteden-opdrachtsoort"
                name="opdrachtsoort"
                data-cy="opdrachtsoort"
                type="text"
              />
              <ValidatedField
                label="Toelichtingaanvullendeopdracht"
                id="startformulieraanbesteden-toelichtingaanvullendeopdracht"
                name="toelichtingaanvullendeopdracht"
                data-cy="toelichtingaanvullendeopdracht"
                type="text"
              />
              <ValidatedField
                label="Toelichtingeenmaligofrepeterend"
                id="startformulieraanbesteden-toelichtingeenmaligofrepeterend"
                name="toelichtingeenmaligofrepeterend"
                data-cy="toelichtingeenmaligofrepeterend"
                type="text"
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/startformulieraanbesteden" replace color="info">
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

export default StartformulieraanbestedenUpdate;
