import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Soortdisciplinairemaatregel from './soortdisciplinairemaatregel';
import SoortdisciplinairemaatregelDetail from './soortdisciplinairemaatregel-detail';
import SoortdisciplinairemaatregelUpdate from './soortdisciplinairemaatregel-update';
import SoortdisciplinairemaatregelDeleteDialog from './soortdisciplinairemaatregel-delete-dialog';

const SoortdisciplinairemaatregelRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Soortdisciplinairemaatregel />} />
    <Route path="new" element={<SoortdisciplinairemaatregelUpdate />} />
    <Route path=":id">
      <Route index element={<SoortdisciplinairemaatregelDetail />} />
      <Route path="edit" element={<SoortdisciplinairemaatregelUpdate />} />
      <Route path="delete" element={<SoortdisciplinairemaatregelDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default SoortdisciplinairemaatregelRoutes;
