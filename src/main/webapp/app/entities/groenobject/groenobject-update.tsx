import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IGroenobject } from 'app/shared/model/groenobject.model';
import { getEntity, updateEntity, createEntity, reset } from './groenobject.reducer';

export const GroenobjectUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const groenobjectEntity = useAppSelector(state => state.groenobject.entity);
  const loading = useAppSelector(state => state.groenobject.loading);
  const updating = useAppSelector(state => state.groenobject.updating);
  const updateSuccess = useAppSelector(state => state.groenobject.updateSuccess);

  const handleClose = () => {
    navigate('/groenobject');
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
      ...groenobjectEntity,
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
          ...groenobjectEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.groenobject.home.createOrEditLabel" data-cy="GroenobjectCreateUpdateHeading">
            Create or edit a Groenobject
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="groenobject-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField
                label="Aantalobstakels"
                id="groenobject-aantalobstakels"
                name="aantalobstakels"
                data-cy="aantalobstakels"
                type="text"
              />
              <ValidatedField label="Aantalzijden" id="groenobject-aantalzijden" name="aantalzijden" data-cy="aantalzijden" type="text" />
              <ValidatedField label="Afvoeren" id="groenobject-afvoeren" name="afvoeren" data-cy="afvoeren" check type="checkbox" />
              <ValidatedField
                label="Bereikbaarheid"
                id="groenobject-bereikbaarheid"
                name="bereikbaarheid"
                data-cy="bereikbaarheid"
                type="text"
              />
              <ValidatedField
                label="Bergendvermogen"
                id="groenobject-bergendvermogen"
                name="bergendvermogen"
                data-cy="bergendvermogen"
                type="text"
              />
              <ValidatedField
                label="Bewerkingspercentage"
                id="groenobject-bewerkingspercentage"
                name="bewerkingspercentage"
                data-cy="bewerkingspercentage"
                type="text"
              />
              <ValidatedField
                label="Bgtfysiekvoorkomen"
                id="groenobject-bgtfysiekvoorkomen"
                name="bgtfysiekvoorkomen"
                data-cy="bgtfysiekvoorkomen"
                type="text"
              />
              <ValidatedField label="Bollen" id="groenobject-bollen" name="bollen" data-cy="bollen" check type="checkbox" />
              <ValidatedField label="Breedte" id="groenobject-breedte" name="breedte" data-cy="breedte" type="text" />
              <ValidatedField
                label="Breedteklassehaag"
                id="groenobject-breedteklassehaag"
                name="breedteklassehaag"
                data-cy="breedteklassehaag"
                type="text"
              />
              <ValidatedField label="Bvc" id="groenobject-bvc" name="bvc" data-cy="bvc" check type="checkbox" />
              <ValidatedField
                label="Cultuurhistorischwaardevol"
                id="groenobject-cultuurhistorischwaardevol"
                name="cultuurhistorischwaardevol"
                data-cy="cultuurhistorischwaardevol"
                type="text"
              />
              <ValidatedField
                label="Draagkrachtig"
                id="groenobject-draagkrachtig"
                name="draagkrachtig"
                data-cy="draagkrachtig"
                check
                type="checkbox"
              />
              <ValidatedField
                label="Ecologischbeheer"
                id="groenobject-ecologischbeheer"
                name="ecologischbeheer"
                data-cy="ecologischbeheer"
                check
                type="checkbox"
              />
              <ValidatedField
                label="Fysiekvoorkomenimgeo"
                id="groenobject-fysiekvoorkomenimgeo"
                name="fysiekvoorkomenimgeo"
                data-cy="fysiekvoorkomenimgeo"
                type="text"
              />
              <ValidatedField
                label="Gewenstsluitingspercentage"
                id="groenobject-gewenstsluitingspercentage"
                name="gewenstsluitingspercentage"
                data-cy="gewenstsluitingspercentage"
                type="text"
              />
              <ValidatedField
                label="Groenobjectbereikbaarheidplus"
                id="groenobject-groenobjectbereikbaarheidplus"
                name="groenobjectbereikbaarheidplus"
                data-cy="groenobjectbereikbaarheidplus"
                type="text"
              />
              <ValidatedField
                label="Groenobjectconstructielaag"
                id="groenobject-groenobjectconstructielaag"
                name="groenobjectconstructielaag"
                data-cy="groenobjectconstructielaag"
                type="text"
              />
              <ValidatedField
                label="Groenobjectrand"
                id="groenobject-groenobjectrand"
                name="groenobjectrand"
                data-cy="groenobjectrand"
                type="text"
              />
              <ValidatedField
                label="Groenobjectsoortnaam"
                id="groenobject-groenobjectsoortnaam"
                name="groenobjectsoortnaam"
                data-cy="groenobjectsoortnaam"
                type="text"
              />
              <ValidatedField
                label="Haagvoetlengte"
                id="groenobject-haagvoetlengte"
                name="haagvoetlengte"
                data-cy="haagvoetlengte"
                type="text"
              />
              <ValidatedField
                label="Haagvoetoppervlakte"
                id="groenobject-haagvoetoppervlakte"
                name="haagvoetoppervlakte"
                data-cy="haagvoetoppervlakte"
                type="text"
              />
              <ValidatedField
                label="Herplantplicht"
                id="groenobject-herplantplicht"
                name="herplantplicht"
                data-cy="herplantplicht"
                check
                type="checkbox"
              />
              <ValidatedField label="Hoogte" id="groenobject-hoogte" name="hoogte" data-cy="hoogte" type="text" />
              <ValidatedField
                label="Hoogteklassehaag"
                id="groenobject-hoogteklassehaag"
                name="hoogteklassehaag"
                data-cy="hoogteklassehaag"
                type="text"
              />
              <ValidatedField
                label="Knipfrequentie"
                id="groenobject-knipfrequentie"
                name="knipfrequentie"
                data-cy="knipfrequentie"
                type="text"
              />
              <ValidatedField
                label="Knipoppervlakte"
                id="groenobject-knipoppervlakte"
                name="knipoppervlakte"
                data-cy="knipoppervlakte"
                type="text"
              />
              <ValidatedField
                label="Kwaliteitsniveauactueel"
                id="groenobject-kwaliteitsniveauactueel"
                name="kwaliteitsniveauactueel"
                data-cy="kwaliteitsniveauactueel"
                type="text"
              />
              <ValidatedField
                label="Kwaliteitsniveaugewenst"
                id="groenobject-kwaliteitsniveaugewenst"
                name="kwaliteitsniveaugewenst"
                data-cy="kwaliteitsniveaugewenst"
                type="text"
              />
              <ValidatedField label="Lengte" id="groenobject-lengte" name="lengte" data-cy="lengte" type="text" />
              <ValidatedField label="Leverancier" id="groenobject-leverancier" name="leverancier" data-cy="leverancier" type="text" />
              <ValidatedField
                label="Maaifrequentie"
                id="groenobject-maaifrequentie"
                name="maaifrequentie"
                data-cy="maaifrequentie"
                type="text"
              />
              <ValidatedField
                label="Maximalevalhoogte"
                id="groenobject-maximalevalhoogte"
                name="maximalevalhoogte"
                data-cy="maximalevalhoogte"
                type="text"
              />
              <ValidatedField
                label="Eobjectnummer"
                id="groenobject-eobjectnummer"
                name="eobjectnummer"
                data-cy="eobjectnummer"
                type="text"
                validate={{
                  maxLength: { value: 20, message: 'This field cannot be longer than 20 characters.' },
                }}
              />
              <ValidatedField label="Obstakels" id="groenobject-obstakels" name="obstakels" data-cy="obstakels" check type="checkbox" />
              <ValidatedField label="Omtrek" id="groenobject-omtrek" name="omtrek" data-cy="omtrek" type="text" />
              <ValidatedField label="Ondergroei" id="groenobject-ondergroei" name="ondergroei" data-cy="ondergroei" type="text" />
              <ValidatedField label="Oppervlakte" id="groenobject-oppervlakte" name="oppervlakte" data-cy="oppervlakte" type="text" />
              <ValidatedField label="Optalud" id="groenobject-optalud" name="optalud" data-cy="optalud" type="text" />
              <ValidatedField label="Taludsteilte" id="groenobject-taludsteilte" name="taludsteilte" data-cy="taludsteilte" type="text" />
              <ValidatedField label="Type" id="groenobject-type" name="type" data-cy="type" type="text" />
              <ValidatedField
                label="Typebewerking"
                id="groenobject-typebewerking"
                name="typebewerking"
                data-cy="typebewerking"
                type="text"
              />
              <ValidatedField
                label="Typeomgevingsrisicoklasse"
                id="groenobject-typeomgevingsrisicoklasse"
                name="typeomgevingsrisicoklasse"
                data-cy="typeomgevingsrisicoklasse"
                type="text"
              />
              <ValidatedField label="Typeplus" id="groenobject-typeplus" name="typeplus" data-cy="typeplus" type="text" />
              <ValidatedField
                label="Typeplus 2"
                id="groenobject-typeplus2"
                name="typeplus2"
                data-cy="typeplus2"
                type="text"
                validate={{
                  maxLength: { value: 100, message: 'This field cannot be longer than 100 characters.' },
                }}
              />
              <ValidatedField
                label="Veiligheidsklasseboom"
                id="groenobject-veiligheidsklasseboom"
                name="veiligheidsklasseboom"
                data-cy="veiligheidsklasseboom"
                type="text"
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/groenobject" replace color="info">
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

export default GroenobjectUpdate;
