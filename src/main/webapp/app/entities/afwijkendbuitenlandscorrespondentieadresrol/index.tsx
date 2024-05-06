import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Afwijkendbuitenlandscorrespondentieadresrol from './afwijkendbuitenlandscorrespondentieadresrol';
import AfwijkendbuitenlandscorrespondentieadresrolDetail from './afwijkendbuitenlandscorrespondentieadresrol-detail';
import AfwijkendbuitenlandscorrespondentieadresrolUpdate from './afwijkendbuitenlandscorrespondentieadresrol-update';
import AfwijkendbuitenlandscorrespondentieadresrolDeleteDialog from './afwijkendbuitenlandscorrespondentieadresrol-delete-dialog';

const AfwijkendbuitenlandscorrespondentieadresrolRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Afwijkendbuitenlandscorrespondentieadresrol />} />
    <Route path="new" element={<AfwijkendbuitenlandscorrespondentieadresrolUpdate />} />
    <Route path=":id">
      <Route index element={<AfwijkendbuitenlandscorrespondentieadresrolDetail />} />
      <Route path="edit" element={<AfwijkendbuitenlandscorrespondentieadresrolUpdate />} />
      <Route path="delete" element={<AfwijkendbuitenlandscorrespondentieadresrolDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default AfwijkendbuitenlandscorrespondentieadresrolRoutes;
